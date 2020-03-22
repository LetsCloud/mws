/**
 * 
 */
package io.crs.mws.client.core.app;

import java.util.logging.Logger;

import javax.inject.Inject;

import gwt.material.design.client.pwa.serviceworker.ServiceEvent;
import gwt.material.design.client.pwa.serviceworker.js.ServiceWorkerRegistration;
import io.crs.mws.client.core.firebase.messaging.MessagingManager;

/**
 * @author CR
 *
 */
public class AppServiceWorkerManager extends AdmServiceWorkerManager {
	private static Logger logger = Logger.getLogger(AppServiceWorkerManager.class.getName());

	private final MessagingManager fcmManager;

	@Inject
	AppServiceWorkerManager(MessagingManager fcmManager) {
		super();
		logger.info("AppServiceWorkerManager()");
		this.fcmManager = fcmManager;
		// Polling Interval should be every 1 minute
		/* setPollingInterval(1000); */
	}

	@Override
	public boolean onRegistered(ServiceEvent event, ServiceWorkerRegistration registration) {
		logger.info("onRegistered()");
		boolean result = super.onRegistered(event, registration);

		if (result) {
			logger.info("Sikeres volt a ServiceWorker regisztrácója, ezért átadjuk a MessagingManager-nek.");
			fcmManager.useServiceWorker(registration);
			registered = true;
		}

		return result;
	}

}
