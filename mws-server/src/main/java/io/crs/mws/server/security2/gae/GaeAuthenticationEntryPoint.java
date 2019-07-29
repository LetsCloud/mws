/**
 * 
 */
package io.crs.mws.server.security2.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author robi
 *
 */
public class GaeAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(GaeAuthenticationEntryPoint.class);

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.info("commence()");

		UserService userService = UserServiceFactory.getUserService();
		logger.info("commence()->userService=" + userService);

		response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
	}
}