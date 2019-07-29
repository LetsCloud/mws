/**
 * 
 */
package io.crs.mws.server.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.crs.mws.shared.dto.AccountDto;

/**
 * @author robi
 *
 */
public class AccountDetails implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final AccountDto account;
	private final List<GrantedAuthority> authorities;
	private final boolean accountNonLocked;
	private String targetUrl;

	public AccountDetails(AccountDto accountDto, List<GrantedAuthority> authorities, boolean accountNonLocked) {
		this.account = accountDto;
		this.authorities = authorities;
		this.accountNonLocked = accountNonLocked;
	}

	public AccountDto getAccount() {
		return account;
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return account.getEnabled();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

}
