package io.crs.mws.client.core.security;

import java.util.logging.Logger;

import javax.inject.Inject;

import io.crs.mws.shared.cnst.Language;
import io.crs.mws.shared.dto.AccountDto;

public class CurrentUser {
	private static Logger logger = Logger.getLogger(CurrentUser.class.getName());

	private AccountDto accountDto;

	private String locale;

	private Language language;

	private boolean loggedIn;

	private boolean registredServiceWorker;

	@Inject
	CurrentUser() {
		logger.info("CurrentUser()");
		loggedIn = false;
		registredServiceWorker = false;
	}

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

	public boolean isRegistredServiceWorker() {
		return registredServiceWorker;
	}

	public void setRegistredServiceWorker(boolean registredServiceWorker) {
		this.registredServiceWorker = registredServiceWorker;
	}

	@Override
	public String toString() {
		return "CurrentUser [accountDto=" + accountDto + ", locale=" + locale + ", language=" + language + ", loggedIn="
				+ loggedIn + "]";
	}

}
