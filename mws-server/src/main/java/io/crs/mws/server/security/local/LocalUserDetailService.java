/**
 * 
 */
package io.crs.mws.server.security.local;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.login.LoginAttemptService;
import io.crs.mws.server.service.AccountService;

/**
 * @author robi
 *
 */
public class LocalUserDetailService implements UserDetailsService {

	private final AccountService accountService;
	private final LoginAttemptService loginAttemptService;

	public LocalUserDetailService(AccountService accountService, LoginAttemptService loginAttemptService) {
		this.accountService = accountService;
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public LocalUser loadUserByUsername(final String loginname) throws UsernameNotFoundException {
		String ip = getRequestRemoteAddr();
		if (loginAttemptService.isBlocked(ip))
			return null;

		Account account = accountService.findByEmail(loginname);
		if (account == null) {
			return null;
		}
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = buildSimpleGrantedAuthorities(account);

		return new LocalUser(account.getEmail(), account.getEmail(), account.getPassword(),
				account.getEnabled(), true, true, true, simpleGrantedAuthorities);
	}

	private List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Account account) {
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		/*
		 * if (account.getRoles() != null) { for (Role role : account.getRoles()) {
		 * simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName())); } }
		 */
		return simpleGrantedAuthorities;
	}

	/**
	 * 
	 * @return private String getClientIP() { String xfHeader =
	 *         request.getHeader("X-Forwarded-For"); if (xfHeader == null) { return
	 *         request.getRemoteAddr(); } return xfHeader.split(",")[0]; }
	 */
	public static String getRequestRemoteAddr() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		return request.getRemoteAddr();
	}
}
