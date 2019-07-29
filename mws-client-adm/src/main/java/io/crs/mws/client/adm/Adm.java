/**
 * 
 */
package io.crs.mws.client.adm;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Defaults;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.PreBootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import gwt.material.design.client.pwa.PwaManager;
import io.crs.mws.client.core.app.AppServiceWorkerManager;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.client.core.util.OauthUtils;
import io.crs.mws.client.core.util.UrlUtils;
import io.crs.mws.shared.cnst.SubSystem;

/**
 * @author CR
 *
 */
public class Adm implements Bootstrapper {
	private static Logger logger = Logger.getLogger(Adm.class.getName());

	private final PlaceManager placeManager;
	private final UserManager userManager;
	private final AppServiceWorkerManager serviceWorkerManager;
	private final AppData appData;

	@Inject
	Adm(PlaceManager placeManager, UserManager userManager, AppServiceWorkerManager serviceWorkerManager,
			AppData appData) {
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
		logger.log(Level.SEVERE, "onBootstrap()-4");
		if (!Strings.isNullOrEmpty(OauthUtils.loadAccessToken())) {
			logger.log(Level.SEVERE, "onBootstrap()-5");
			userManager.load(() -> placeManager.revealCurrentPlace());
		} else {
			logger.log(Level.SEVERE, "onBootstrap()-6");
			placeManager.revealCurrentPlace();
		}
		logger.log(Level.SEVERE, "onBootstrap()-7");
	}
}