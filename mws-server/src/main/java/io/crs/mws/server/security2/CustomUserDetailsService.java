/**
 * 
 */
package io.crs.mws.server.security2;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.cnst.Role;

/**
 * @author robi
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	AccountService accountRepository;

	@Autowired
	CustomUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("loadUserByUsername()->email=" + email);
		Account user = accountRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return UserPrincipal.create(user, getAuthority());
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_USER));
	}
}
