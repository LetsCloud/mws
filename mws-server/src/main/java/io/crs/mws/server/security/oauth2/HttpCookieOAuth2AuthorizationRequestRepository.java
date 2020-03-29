/**
 * 
 */
package io.crs.mws.server.security.oauth2;

import com.nimbusds.oauth2.sdk.util.StringUtils;

import io.crs.mws.server.security.util.CookieUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The OAuth2 protocol recommends using a state parameter to prevent CSRF
 * attacks. During authentication, the application sends this parameter in the
 * authorization request, and the OAuth2 provider returns this parameter
 * unchanged in the OAuth2 callback.
 * 
 * The application compares the value of the state parameter returned from the
 * OAuth2 provider with the value that it had sent initially. If they don’t
 * match then it denies the authentication request.
 * 
 * To achieve this flow, the application needs to store the state parameter
 * somewhere so that it can later compare it with the state returned from the
 * OAuth2 provider.
 * 
 * We’ll be storing the state as well as the redirect_uri in a short-lived
 * cookie. The following class provides functionality for storing the
 * authorization request in cookies and retrieving it.
 * 
 */
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
		implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
	private static final Logger logger = LoggerFactory.getLogger(HttpCookieOAuth2AuthorizationRequestRepository.class);

	public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
	public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
	private static final int cookieExpireSeconds = 180;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		logger.info("loadAuthorizationRequest()");
		return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
				.map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class)).orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("saveAuthorizationRequest()");
		Assert.notNull(request, "request cannot be null");
		Assert.notNull(response, "response cannot be null");

		if (authorizationRequest == null) {
			CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
			CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
			return;
		}

		CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
				CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);
		String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
		logger.info("saveAuthorizationRequest()->redirectUriAfterLogin=" + redirectUriAfterLogin);
		if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
			CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
		}
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
		logger.info("removeAuthorizationRequest()");
		Assert.notNull(request, "request cannot be null");
		/*
		 * Ideally, the saved OAuth2AuthorizationRequest should be removed in this
		 * method. Since we're saving the OAuth2AuthorizationRequest in cookies, we need
		 * access to the HttpServletResponse to clear them. But that is not passed to
		 * this method. Therefore, We'll clear the cookies in
		 * Oauth2AuthenticationSuccessHandler instead.
		 */
		return this.loadAuthorizationRequest(request);
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
	}
}