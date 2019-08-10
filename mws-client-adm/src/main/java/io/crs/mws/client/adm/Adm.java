/**
 * 
 */
package io.crs.mws.client.adm;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import io.crs.mws.client.core.AbstractAppBootstrapper;
import io.crs.mws.client.core.app.AppServiceWorkerManager;
import io.crs.mws.client.core.firebase.messaging.MessagingManager;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.shared.cnst.SubSystem;

/**
 * @author CR
 *
 */
public class Adm extends AbstractAppBootstrapper {

	@Inject
	Adm(EventBus eventBus, PlaceManager placeManager, UserManager userManager,
			AppServiceWorkerManager serviceWorkerManager, MessagingManager messagingManager, AppData appData) {
		super(eventBus, placeManager, userManager, serviceWorkerManager, messagingManager, appData);
		setAppCode(SubSystem.ADM);
	}
}