/**
 * 
 */
package io.crs.mws.server.security2.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.UriComponentsBuilder;

import io.crs.mws.server.security2.AppProperties;
import io.crs.mws.server.security2.TokenProvider;
import io.crs.mws.server.security2.oauth2.exception.BadRequestException;
import io.crs.mws.server.security2.util.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static io.crs.mws.server.security2.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * @author robi
 *
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

	private TokenProvider tokenProvider;

	private AppProperties appProperties;

	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Autowired
	OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, AppProperties appProperties,
			HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		this.tokenProvider = tokenProvider;
		this.appProperties = appProperties;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);
		logger.info("onAuthenticationSuccess()->targetUrl=" + targetUrl);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);
		logger.info("determineTargetUrl()->redirectUri=" + redirectUri.get());

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new BadRequestException(
					"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
		logger.info("determineTargetUrl()->targetUrl1=" + targetUrl);
		targetUrl = targetUrl + "#oauth2redirect";
		logger.info("determineTargetUrl()->targetUrl2=" + targetUrl);

		String token = tokenProvider.createToken(authentication);

		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token);
		logger.info("determineTargetUrl()->ucb=" + ucb.toString());

//		return ucb.build().toUriString();
		return targetUrl+"?token="+token;
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		logger.info("uri=" + uri);
		URI clientRedirectUri = URI.create(uri);

		return appProperties.getOauth2().getAuthorizedRedirectUris().stream().anyMatch(authorizedRedirectUri -> {
			// Only validate host and port. Let the clients use different paths if they want
			// to
			logger.info("authorizedRedirectUri=" + authorizedRedirectUri);
			URI authorizedURI = URI.create(authorizedRedirectUri);
			if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
					&& authorizedURI.getPort() == clientRedirectUri.getPort()) {
				return true;
			}
			return false;
		});
	}
}