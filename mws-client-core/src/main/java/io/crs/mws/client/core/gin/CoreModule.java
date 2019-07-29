/**
 * 
 */
package io.crs.mws.client.core.gin;

import java.util.logging.Logger;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;
import com.gwtplatform.mvp.shared.proxy.RouteTokenFormatter;

import io.crs.mws.client.core.app.AppServiceWorkerManager;
import io.crs.mws.client.core.filter.FilterModule;
import io.crs.mws.client.core.firebase.messaging.MessagingManager;
import io.crs.mws.client.core.menu.MenuModule;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.security.UserManager;

/**
 * @author CR
 *
 */
public class CoreModule extends AbstractPresenterModule {
	private static Logger logger = Logger.getLogger(CoreModule.class.getName());

	@Override
	protected void configure() {
		logger.info("CoreModule.configure(");
		install(new DefaultModule.Builder().placeManager(DefaultPlaceManager.class)
				.tokenFormatter(RouteTokenFormatter.class).build());

		bind(AppData.class).asEagerSingleton();
		bind(CurrentUser.class).asEagerSingleton();

		bind(UserManager.class).asEagerSingleton();
		bind(MessagingManager.class).asEagerSingleton();
		bind(AppServiceWorkerManager.class).asEagerSingleton();

		bind(ResourceLoader.class).asEagerSingleton();

		install(new MenuModule());
		install(new FilterModule());
	}
}
