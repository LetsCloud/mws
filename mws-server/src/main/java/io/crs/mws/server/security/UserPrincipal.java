/**
 * 
 */
package io.crs.mws.server.security;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import io.crs.mws.server.entity.Account;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class UserPrincipal implements OAuth2User, UserDetails {
	private static final Logger logger = LoggerFactory.getLogger(UserPrincipal.class);

	private String email;
	private String password;

	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public UserPrincipal(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(Account user, Collection<? extends GrantedAuthority> authorities) {
		logger.info("create()->user=" + user);

		return new UserPrincipal(user.getEmail(), user.getPassword(), authorities);
	}

	public static UserPrincipal create(Account user, Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
		UserPrincipal userPrincipal = UserPrincipal.create(user, authorities);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return email;
	}
}