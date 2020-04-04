/**
 * 
 */
package io.crs.mws.client.core.app;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;

import gwt.material.design.client.pwa.serviceworker.ServiceEvent;
import gwt.material.design.client.pwa.serviceworker.js.ServiceWorkerRegistration;
import io.crs.mws.client.core.event.CurrentUserEvent;
import io.crs.mws.client.core.firebase.messaging.MessagingManager;

/**
 * @author CR
 *
 */
public class AppServiceWorkerManager extends BaseServiceWorkerManager {
	private static Logger logger = Logger.getLogger(AppServiceWorkerManager.class.getName());

	private final EventBus eventBus;
	private final MessagingManager messagingManager;

	@Inject
	AppServiceWorkerManager(EventBus eventBus, MessagingManager messagingManager) {
		super();
		logger.info("AppServiceWorkerManager()");
		this.messagingManager = messagingManager;
		this.eventBus = eventBus;
		// Polling Interval should be every 1 minute
		/* setPollingInterval(1000); */
	}

	@Override
	public boolean onRegistered(ServiceEvent event, ServiceWorkerRegistration registration) {
		boolean result = super.onRegistered(event, registration);
		logger.info("AppServiceWorkerManager().onRegistered()");

		if (result) {
			logger.info("Sikeres ServiceWorker regisztrácó, átadás MessagingManager-nek.");
			registered = true;
			messagingManager.useServiceWorker(registration);
			eventBus.fireEvent(new CurrentUserEvent(true));
		}

		return result;
	}

}
