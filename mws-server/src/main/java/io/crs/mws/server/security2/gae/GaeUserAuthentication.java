/**
 * 
 */
package io.crs.mws.server.security2.gae;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author robi
 *
 */
/**
 * Authentication object representing a fully-authenticated user.
 *
 * @author Luke Taylor
 */
@SuppressWarnings("serial")
public class GaeUserAuthentication implements Authentication {
	private static final Logger logger = LoggerFactory.getLogger(GaeUserAuthentication.class);

	private final GaeUser principal;
	private final Object details;
	private boolean authenticated;

	public GaeUserAuthentication(GaeUser principal, Object details) {
		logger.info("GaeUserAuthentication()");
		this.principal = principal;
		this.details = details;
		authenticated = true;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		logger.info("getAuthorities()");
		return new HashSet<>(principal.getAuthorities());
	}

	public Object getCredentials() {
		logger.info("getCredentials()");
		throw new UnsupportedOperationException();
	}

	public Object getDetails() {
		logger.info("getDetails()");
		return null;
	}

	public Object getPrincipal() {
		logger.info("getPrincipal()");
		return principal;
	}

	public boolean isAuthenticated() {
		logger.info("isAuthenticated()");
		return authenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		logger.info("setAuthenticated()");
		authenticated = isAuthenticated;
	}

	public String getName() {
		logger.info("getName()");
		return principal.getUserId();
	}

	@Override
	public String toString() {
		return "GaeUserAuthentication{" + "principal=" + principal + ", details="
				+ details + ", authenticated=" + authenticated + '}';
	}
}
