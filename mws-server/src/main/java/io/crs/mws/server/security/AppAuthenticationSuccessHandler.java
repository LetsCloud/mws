/**
 * 
 */
package io.crs.mws.server.security;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

/**
 * @author robi
 *
 */
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationSuccessHandler.class);

	private static final String TARGET_URL = "targetUrl";
	private static final String LAUNCHBOARD_URL = "launchboard";

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = LAUNCHBOARD_URL;

		Map<String, String[]> params = request.getParameterMap();
		for (Entry<String, String[]> param : params.entrySet()) {
			logger.info("param->" + param.getKey());
		}

		logger.info("targetUrl=" + request.getParameter(TARGET_URL));

		if (StringUtils.hasText(request.getParameter(TARGET_URL))) {
			byte[] decodedBytes = Base64Utils.decodeFromString(request.getParameter(TARGET_URL));
			targetUrl = new String(decodedBytes);
		}
	
		logger.info("targetUrl2=" + targetUrl);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
