/**
 * 
 */
package io.crs.mws.client.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Defaults;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.PreBootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.pwa.PwaManager;
import io.crs.mws.client.core.app.AppServiceWorkerManager;
import io.crs.mws.client.core.firebase.messaging.MessagingManager;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.client.core.util.OauthUtils;
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
	private final AppServiceWorkerManager serviceWorkerManager;
	private final MessagingManager messagingManager;
	private final AppData appData;

	public AbstractAppBootstrapper(EventBus eventBus, PlaceManager placeManager, UserManager userManager,
			AppServiceWorkerManager serviceWorkerManager, MessagingManager messagingManager, AppData appData) {
		this.eventBus = eventBus;
		this.placeManager = placeManager;
		this.userManager = userManager;
		this.serviceWorkerManager = serviceWorkerManager;
		this.messagingManager = messagingManager;
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
		Defaults.setServiceRoot(UrlUtils.getBaseUrl());

		Timer t = new Timer() {
			@Override
			public void run() {
				PwaManager.getInstance().setServiceWorker(serviceWorkerManager).setWebManifest(appData.getManifest())
						.load();
			}
		};
		t.schedule(500);

		if (!Strings.isNullOrEmpty(OauthUtils.loadAccessToken())) {
			userManager.load(() -> placeManager.revealCurrentPlace());
		} else {
			placeManager.revealCurrentPlace();
		}
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