/**
 * 
 */
package io.crs.mws.client.core.gin;

import java.util.logging.Logger;

import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;
import com.gwtplatform.mvp.shared.proxy.RouteTokenFormatter;

import io.crs.mws.client.core.CoreNameTokens;
import io.crs.mws.client.core.filter.FilterModule;
import io.crs.mws.client.core.menu.MenuModule;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.CurrentUser;

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

		bindConstant().annotatedWith(DefaultPlace.class).to(CoreNameTokens.HOME);
		bindConstant().annotatedWith(ErrorPlace.class).to(CoreNameTokens.LOGIN);
		bindConstant().annotatedWith(UnauthorizedPlace.class).to(CoreNameTokens.LOGIN);


		bind(AppData.class).asEagerSingleton();

		bind(CurrentUser.class).asEagerSingleton();
		
		bind(ResourceLoader.class).asEagerSingleton();

		install(new MenuModule());
		install(new FilterModule());
	}
}
