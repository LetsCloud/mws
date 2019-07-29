/**
 * 
 */
package io.crs.mws.server.security.social;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import io.crs.mws.server.security.local.LocalUser;

/**
 * @author robi
 *
 */
public class SocialUserDetailService implements SocialUserDetailsService {

	private UserDetailsService userDetailService;

	public void setUserDetailService(UserDetailsService userDetailService) {
		this.userDetailService = userDetailService;
	}

	@Override
	public SocialUserDetails loadUserByUserId(final String userId) throws UsernameNotFoundException {
		LocalUser user = (LocalUser) userDetailService.loadUserByUsername(userId);
		if (user == null) {
			throw new SocialAuthenticationException("No local user mapped with social user " + userId);
		}
		return new AppSocialUser(user.getUserId(), user.getUsername(), user.getPassword(), user.isEnabled(),
				user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
				user.getAuthorities());
	}
}
