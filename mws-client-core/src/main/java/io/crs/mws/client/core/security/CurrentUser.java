package io.crs.mws.client.core.security;

import java.util.logging.Logger;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;

import io.crs.mws.client.core.service.AuthService;
import io.crs.mws.shared.cnst.Language;
import io.crs.mws.shared.dto.AccountDto;

public class CurrentUser {
	private static Logger logger = Logger.getLogger(CurrentUser.class.getName());

	private AccountDto accountDto;

	private String locale;

	private Language language;

	private boolean loggedIn;

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "CurrentUser [accountDto=" + accountDto + ", locale=" + locale + ", language=" + language + ", loggedIn="
				+ loggedIn + "]";
	}

}
