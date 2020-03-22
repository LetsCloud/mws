/**
 * 
 */
package io.crs.mws.client.app;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import io.crs.mws.client.core.AbstractAppBootstrapper;
import io.crs.mws.client.core.app.AppServiceWorkerManager;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.shared.cnst.SubSystem;

/**
 * @author CR
 *
 */
public class App extends AbstractAppBootstrapper {

	@Inject
	App(EventBus eventBus, PlaceManager placeManager, UserManager userManager,
			AppServiceWorkerManager serviceWorkerManager, AppData appData) {
		super(eventBus, placeManager, userManager, serviceWorkerManager, appData);
		setAppCode(SubSystem.APP);
	}

}