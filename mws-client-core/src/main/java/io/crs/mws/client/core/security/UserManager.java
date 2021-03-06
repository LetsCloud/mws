/**
 * 
 */
package io.crs.mws.client.core.security;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.web.bindery.event.shared.EventBus;

import io.crs.mws.client.core.event.CurrentUserEvent;
import io.crs.mws.client.core.promise.xgwt.Fn;
import io.crs.mws.client.core.service.AuthService;
import io.crs.mws.client.core.util.OAuth2Utils;
import io.crs.mws.shared.cnst.Language;
import io.crs.mws.shared.dto.AccountDto;
import io.crs.mws.shared.dto.auth.AuthResponse;
import io.crs.mws.shared.dto.auth.LoginRequest;

/**
 * @author robi
 *
 */
public class UserManager {
	private static Logger logger = Logger.getLogger(UserManager.class.getName());

	private static final AuthService AUTH_SERVICE = GWT.create(AuthService.class);

	private final EventBus eventBus;
	protected final CurrentUser currentUser;

	@Inject
	UserManager(EventBus eventBus, CurrentUser currentUser) {
		logger.info("UserManager()");
		this.eventBus = eventBus;
		this.currentUser = currentUser;
	}

	/**
	 * 
	 * @param isLogin
	 */
	public void loadCurrentAccount(Fn.NoArg callback) {
		logger.info("UserManager().loadCurrentAccount()");
		AUTH_SERVICE.getCurrentUser(new MethodCallback<AccountDto>() {

			@Override
			public void onSuccess(Method method, AccountDto response) {
				logger.info("UserManager().loadCurrentAccount().onSuccess()");
				if (response == null) {
					logger.info("UserManager().loadCurrentAccount().onSuccess()->(result == null)");
					currentUser.setLoggedIn(false);
					return;
				}
				String locale = LocaleInfo.getCurrentLocale().getLocaleName();

				currentUser.setLocale(locale);
				currentUser.setLanguage(Language.en);
				if (locale.startsWith("uk"))
					currentUser.setLanguage(Language.uk);
				if (locale.startsWith("hu"))
					currentUser.setLanguage(Language.hu);

				currentUser.setAccountDto(response);
				currentUser.setLoggedIn(true);
				
				onSuccessAccountLoad(callback);

				eventBus.fireEvent(new CurrentUserEvent(true));
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.info("UserManager().loadCurrentAccount().onFailure()->caught.getMessage()="
						+ exception.getMessage());
				currentUser.setLoggedIn(false);
				onFailedAccountLoad(callback);
				eventBus.fireEvent(new CurrentUserEvent(false));
			}
		});
	}

	protected void onSuccessAccountLoad(Fn.NoArg callback) {
		callback.call();
	}

	protected void onFailedAccountLoad(Fn.NoArg callback) {
		callback.call();
	}
	
	public void login(LoginRequest loginRequest, Fn.NoArg callback, Fn.NoArg faildCallback) {
		logger.info("UserManager().login()");
		AUTH_SERVICE.login(loginRequest, new MethodCallback<AuthResponse>() {

			@Override
			public void onSuccess(Method method, AuthResponse response) {
				logger.info("UserManager().login().onSuccess()");
				OAuth2Utils.storeAccessToken(response.getAccessToken());
				loadCurrentAccount(callback);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				faildCallback.call();
			}
		});
	}
}
