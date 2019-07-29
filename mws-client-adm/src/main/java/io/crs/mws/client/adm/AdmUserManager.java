/**
 * 
 */
package io.crs.mws.client.adm;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;

import io.crs.mws.client.adm.service.AuthService;
import io.crs.mws.client.core.promise.xgwt.Fn;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.shared.cnst.Language;
import io.crs.mws.shared.dto.AccountDto;

/**
 * @author robi
 *
 */
public class AdmUserManager {
	private static Logger logger = Logger.getLogger(AdmUserManager.class.getName());

	private static final AuthService AUTH_SERVICE = GWT.create(AuthService.class);

	private final CurrentUser currentUser;

	@Inject
	AdmUserManager(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * 
	 * @param isLogin
	 */
	public void load(Fn.NoArg callback) {
		logger.info("UserManager().load()");
		AUTH_SERVICE.getCurrentUser(new MethodCallback<AccountDto>() {

			@Override
			public void onSuccess(Method method, AccountDto response) {
				logger.info("UserManager().load().onSuccess()");
				if (response == null) {
					logger.info("UserManager().load().onSuccess()->(result == null)");
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

//				messagingManager.initFirebase(currentUser.getAccountDto().getWebSafeKey(), callback);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.info("AbstractAppPresenter().checkCurrentUser().onFailure()->caught.getMessage()="
						+ exception.getMessage());
				currentUser.setLoggedIn(false);
			}
		});
	}
}
