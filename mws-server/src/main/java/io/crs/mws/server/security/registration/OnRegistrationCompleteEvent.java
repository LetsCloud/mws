/**
 * 
 */
package io.crs.mws.server.security.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import io.crs.mws.server.entity.Account;

/**
 * @author CR
 *
 */
@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	private final String accountKey;
	private final String appUrl;
	private final Locale locale;

	public OnRegistrationCompleteEvent(String accountKey, Locale locale, String appUrl) {
		super(accountKey);

		this.accountKey = accountKey;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getAccountKey() {
		return accountKey;
	}

}