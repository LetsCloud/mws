/**
 * 
 */
package io.crs.mws.client.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Defaults;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.PreBootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.pwa.PwaManager;
import io.crs.mws.client.core.app.BaseServiceWorkerManager;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.client.core.util.OAuth2Utils;
import io.crs.mws.client.core.util.UrlUtils;

/**
 * @author robi
 *
 */
public abstract class AbstractAppBootstrapper implements Bootstrapper {
	private static Logger logger = Logger.getLogger(AbstractAppBootstrapper.class.getName());

	public static final String TARGET_URL = "targetUrl";
	public static final String LOGIN_URL = "/login";

	private final EventBus eventBus;
	private final PlaceManager placeManager;
	private final UserManager userManager;
	private final BaseServiceWorkerManager serviceWorkerManager;
	private final AppData appData;

	public AbstractAppBootstrapper(EventBus eventBus, PlaceManager placeManager, UserManager userManager,
			BaseServiceWorkerManager serviceWorkerManager, AppData appData) {
		this.eventBus = eventBus;
		this.placeManager = placeManager;
		this.userManager = userManager;
		this.serviceWorkerManager = serviceWorkerManager;
		this.appData = appData;
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
		logger.info("AbstractAppBootstrapper().onBootstrap()");
		Defaults.setServiceRoot(UrlUtils.getBaseUrl());
		Defaults.setDateFormat(null);

		// PwaManager késleltetett létrehozása, a ServiceWorker betöltés bevárása miatt. 
		Scheduler.get().scheduleDeferred(() -> PwaManager.getInstance().setServiceWorker(serviceWorkerManager)
				.setWebManifest(appData.getManifest()).load());

		// Kiolvassuk a sütiben tárolt AccessToken-t
		String accessToken = OAuth2Utils.loadAccessToken();

		if (!Strings.isNullOrEmpty(accessToken)) {
			// Ha van  AccessToken, akkor beolvassuk a bejelentkezett előfizető adatait
			userManager.loadCurrentAccount(() -> placeManager.revealCurrentPlace());
			return;
		}

		placeManager.revealCurrentPlace();
	}

	protected void setAppCode(String code) {
		appData.setAppCode(code);
	}

	public static native String btoa(String b64) /*-{
													return btoa(b64);
													}-*/;

	public static native String atob(String encodedData) /*-{
															return atob(encodedData);
															}-*/;
}