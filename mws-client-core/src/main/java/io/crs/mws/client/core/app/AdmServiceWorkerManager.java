/**
 * 
 */
package io.crs.mws.client.core.app;

import java.util.logging.Logger;

import javax.inject.Inject;

import gwt.material.design.client.pwa.serviceworker.ServiceEvent;
import gwt.material.design.client.pwa.serviceworker.ServiceWorkerManager;
import gwt.material.design.client.pwa.serviceworker.js.ServiceWorkerRegistration;

/**
 * @author robi
 *
 */
public class AdmServiceWorkerManager extends ServiceWorkerManager {
	private static Logger logger = Logger.getLogger(AdmServiceWorkerManager.class.getName());

//	private static final String SW_PATH="service-worker.js";
	protected static final String SW_PATH = "../service-worker.js";

	protected Boolean registered;
	
	@Inject
	AdmServiceWorkerManager() {
		super(SW_PATH);
		logger.info("BaseServiceWorkerManager()");
		this.registered = false;		
	}

	@Override
	public boolean onRegistered(ServiceEvent event, ServiceWorkerRegistration registration) {
		logger.info("onRegistered()");
		boolean result = super.onRegistered(event, registration);

		return result;
	}

	/*
	 * FCM
	 */
	public Boolean isRegistered() {
//		logger.info("isRegistered()=" + registered);
		return registered;
	}

}
