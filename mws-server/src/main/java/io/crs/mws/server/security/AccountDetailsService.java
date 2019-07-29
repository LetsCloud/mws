/**
 * 
 */
package io.crs.mws.server.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.login.LoginAttemptService;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.dto.AccountDto;

/**
 * @author robi
 *
 */
public class AccountDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(AccountDetailsService.class);
	
	@Autowired
	private HttpServletRequest request;
	
	private final AccountService accountService;

	private LoginAttemptService loginAttemptService;

	private final ModelMapper modelMapper;

	AccountDetailsService(AccountService accountService, LoginAttemptService loginAttemptService,
			ModelMapper modelMapper) {
		logger.info("AccountDetailsService()");
		this.accountService = accountService;
		this.loginAttemptService = loginAttemptService;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
		logger.info("loadUserByUsername()->input=" + input);
		String ip = getRequestRemoteAddr();
		boolean accountNonLocked = !loginAttemptService.isBlocked(ip);
		/*
		 * String[] split = input.split(":"); if (split.length < 2) throw new
		 * UsernameNotFoundException("Must specify both username and corporate domain identifier"
		 * );
		 * 
		 * String username = split[0]; String corporateId = split[1];
		 */
		Account account = accountService.findByEmail(input);
		logger.info("loadUserByUsername()->appUser=" + account);
		if (account == null)
			throw new UsernameNotFoundException("User not found");

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		/*
		 * List<String> permissions = userService.getPermissions(appUser.getUsername());
		 * 
		 * for (String permission : permissions) { grantedAuthorities.add(new
		 * SimpleGrantedAuthority(permission)); }
		 */
		AccountDto dto = modelMapper.map(account, AccountDto.class);
		AccountDetails aud = new AccountDetails(dto, grantedAuthorities, accountNonLocked);

		if (request != null)
		aud.setTargetUrl(request.getServerName());
		return aud;
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
