/**
 * 
 */
package io.crs.mws.client.adm;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.TextCallback;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.DOM;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.PreBootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import io.crs.mws.client.adm.service.AuthService;
import io.crs.mws.client.core.app.AppServiceWorkerManager;
import io.crs.mws.client.core.event.CurrentUserEvent;
import io.crs.mws.client.core.promise.xgwt.Fn;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.client.core.util.OauthUtils;
import io.crs.mws.client.core.util.UrlUtils;
import io.crs.mws.shared.cnst.Language;
import io.crs.mws.shared.cnst.SubSystem;
import io.crs.mws.shared.dto.AccountDto;

/**
 * @author CR
 *
 */
public class Adm implements Bootstrapper {
	private static Logger logger = Logger.getLogger(Adm.class.getName());

	private static final AuthService AUTH_SERVICE = GWT.create(AuthService.class);

	private final PlaceManager placeManager;
	private final UserManager userManager;
	private final AppServiceWorkerManager serviceWorkerManager;
	private final AppData appData;
	private final CurrentUser currentUser;

	@Inject
	Adm(PlaceManager placeManager, UserManager userManager, AppServiceWorkerManager serviceWorkerManager,
			AppData appData, CurrentUser currentUser) {
		this.placeManager = placeManager;
		this.userManager = userManager;
		this.serviceWorkerManager = serviceWorkerManager;
		this.appData = appData;
		this.currentUser = currentUser;
	}

	public static class PreApplicationImpl implements PreBootstrapper {
		private static Logger logger = Logger.getLogger(PreApplicationImpl.class.getName());

		@Override
		public void onPreBootstrap() {
			GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
				public void onUncaughtException(Throwable e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			});
		}
	}

	@Override
	public void onBootstrap() {
		appData.setAppCode(SubSystem.ADM);
		logger.log(Level.SEVERE, "onBootstrap()");
		Defaults.setServiceRoot(UrlUtils.getBaseUrl());

		logger.log(Level.SEVERE, "onBootstrap()-3");
		/*
		Timer t = new Timer() {
			@Override
			public void run() {
				PwaManager.getInstance().setServiceWorker(serviceWorkerManager).setWebManifest(appData.getManifest())
						.load();
			}
		};
		t.schedule(500);
*/
		
		if (!Strings.isNullOrEmpty(OauthUtils.loadAccessToken())) {
			logger.log(Level.SEVERE, "onBootstrap()-5");
			userManager.load(() -> placeManager.revealCurrentPlace());
		} else {
			logger.log(Level.SEVERE, "onBootstrap()-6");
			placeManager.revealCurrentPlace();
		}
		logger.log(Level.SEVERE, "onBootstrap()-7");
	}
	
	public void load(Fn.NoArg callback) {
		logger.info("UserManager().load()");
		@SuppressWarnings("deprecation")
		com.google.gwt.user.client.Element splash = DOM.getElementById("splashscreen");
		if (splash != null)
			splash.removeFromParent();

		AUTH_SERVICE.isLoggedIn(new TextCallback() {

			@Override
			public void onSuccess(Method method, String response) {
				logger.info("UserManager().load().onSuccess()");

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