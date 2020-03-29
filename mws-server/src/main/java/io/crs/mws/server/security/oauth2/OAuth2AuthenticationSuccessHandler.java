/**
 * 
 */
package io.crs.mws.server.security.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import io.crs.mws.server.config.AppProperties;
import io.crs.mws.server.security.TokenProvider;
import io.crs.mws.server.security.oauth2.exception.BadRequestException;
import io.crs.mws.server.security.util.CookieUtils;
import io.crs.mws.shared.cnst.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static io.crs.mws.server.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

/**
 * On successful authentication, Spring security invokes the
 * onAuthenticationSuccess() method of the OAuth2AuthenticationSuccessHandler
 * configured in SecurityConfig.
 * 
 * In this method, we perform some validations, create a JWT authentication
 * token, and redirect the user to the redirect_uri specified by the client with
 * the JWT token added in the query string -
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

		// A korábbi autentikációs adatok törlése
		clearAuthenticationAttributes(request, response);

		// Átirányítás
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	/**
	 * 
	 */
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		// Kiolvassuk a
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);
		logger.info("determineTargetUrl()->redirectUri=" + redirectUri.get());

		// Leellenőrizzük, hogy a redirectUri felhatalmazott e
		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new BadRequestException(
					"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		// Ha kapott redirectUri null, akkor a defaultTargetUrl játszik
		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		// A GWT URL kezelési technikája miatt ki kell dekorálni az URL-t.
		// Mivel az UriComponentsBuilder nem képes kezelni #-t, manuálisan kell
		// hozzáadni a
		// megkapott targetUrl-hez a GWTP oldal name token-ét.
		targetUrl = targetUrl + "#" + Constants.OAUTH2_REDIRECT;

		String token = tokenProvider.createToken(authentication);

//		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(targetUrl).queryParam(Constants.OAUTH2_TOKEN, token);
//		return ucb.build().toUriString();

		return targetUrl + "?" + Constants.OAUTH2_TOKEN + "=" + token;
	}

	/**
	 * Removes temporary authentication-related data which may have been stored in
	 * the session during the authentication process.
	 * 
	 * @param request
	 * @param response
	 */
	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		// Az OAuth2 autentikációs paramétereket tartalmazó sütiket is törölni kell
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	/**
	 * Lellenőrzi, hogya megadott URI felhatalmazott e
	 * 
	 * @param uri
	 * @return
	 */
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