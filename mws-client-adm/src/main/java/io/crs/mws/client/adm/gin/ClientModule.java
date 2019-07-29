/**
 * 
 */
package io.crs.mws.client.adm.gin;

import java.util.logging.Logger;

import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.adm.AdmModule;
import io.crs.mws.client.adm.AdmNameTokens;
import io.crs.mws.client.adm.config.system.SystemConfigModule;
import io.crs.mws.client.adm.dashboard.DashboardModule;
import io.crs.mws.client.core.gin.CoreModule;

/**
 * @author CR
 *
 */
public class ClientModule extends AbstractPresenterModule {
	private static Logger logger = Logger.getLogger(ClientModule.class.getName());

	@Override
	protected void configure() {
		logger.info("ClientModule().configure(");
		bindConstant().annotatedWith(DefaultPlace.class).to(AdmNameTokens.HOME);
		bindConstant().annotatedWith(ErrorPlace.class).to(io.crs.mws.client.core.NameTokens.LOGIN);
		bindConstant().annotatedWith(UnauthorizedPlace.class).to(io.crs.mws.client.core.NameTokens.LOGIN);

        install(new CoreModule());

		bind(ResourceLoader.class).asEagerSingleton();

		install(new AdmModule());

		install(new DashboardModule());

		install(new SystemConfigModule());
	}
}
