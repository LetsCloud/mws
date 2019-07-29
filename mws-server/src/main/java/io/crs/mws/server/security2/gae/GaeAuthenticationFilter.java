/**
 * 
 */
package io.crs.mws.server.security2.gae;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author Luke Taylor
 */
public class GaeAuthenticationFilter extends GenericFilterBean {
	private static final String REGISTRATION_URL = "/register.htm";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();
	private AuthenticationManager authenticationManager;
	private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("doFilter()");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.info("doFilter()->authentication=" + authentication);

		User googleUser = UserServiceFactory.getUserService().getCurrentUser();
		logger.info("doFilter()->googleUser=" + googleUser);

		// Ha valaki bejelntkezett és NEM GaeUser, akkor törli a SecurityContext-t és
		// érvényteleníti a munkamanetet, azaz újra be kell jelentkezni
		if (authentication != null && !loggedInUserMatchesGaeUser(authentication, googleUser)) {
			logger.info("doFilter()->(authentication != null)");
			SecurityContextHolder.clearContext();
			authentication = null;
			((HttpServletRequest) request).getSession().invalidate();
		}

		if (authentication == null) {
			logger.info("doFilter()->(authentication == null)");
			if (googleUser != null) {
				logger.info("Currently logged on to GAE as user " + googleUser);
				logger.info("Authenticating to Spring Security");
				// User has returned after authenticating via GAE. Need to authenticate
				// through Spring Security.
				PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
				logger.info("doFilter()->token=" + token);

				token.setDetails(ads.buildDetails((HttpServletRequest) request));
				logger.info("doFilter()->token2=" + token);

				try {
					authentication = authenticationManager.authenticate(token);
					logger.info("doFilter()->authentication=" + authentication);

					SecurityContextHolder.getContext().setAuthentication(authentication);
					logger.info("doFilter()->authentication2=" + authentication);

					if (authentication.getAuthorities().contains(AppRole.NEW_USER)) {
						logger.info("New user authenticated. Redirecting to registration page");
						((HttpServletResponse) response).sendRedirect(REGISTRATION_URL);

						return;
					}

				} catch (AuthenticationException e) {
					logger.info("doFilter()->AuthenticationException");
					e.printStackTrace();
					failureHandler.onAuthenticationFailure((HttpServletRequest) request, (HttpServletResponse) response,
							e);

					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	private boolean loggedInUserMatchesGaeUser(Authentication authentication, User googleUser) {
		assert authentication != null;

		if (googleUser == null) {
			// User has logged out of GAE but is still logged into application
			return false;
		}

		GaeUser gaeUser = (GaeUser) authentication.getPrincipal();

		if (!gaeUser.getEmail().equals(googleUser.getEmail())) {
			return false;
		}

		return true;

	}

	@Override
	public void afterPropertiesSet() throws ServletException {
		Assert.notNull(authenticationManager, "AuthenticationManager must be set");
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}
}