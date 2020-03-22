/**
 * 
 */
package io.crs.mws.client.adm.gin;

import java.util.logging.Logger;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.adm.AdmModule;
import io.crs.mws.client.adm.config.system.SystemConfigModule;
import io.crs.mws.client.adm.dashboard.DashboardModule;
import io.crs.mws.client.adm.login.LoginModule;
import io.crs.mws.client.core.app.AdmServiceWorkerManager;
import io.crs.mws.client.core.gin.CoreModule;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.client.core.security.oauth2redirect.Oauth2RedirectModule;

/**
 * @author CR
 *
 */
public class ClientModule extends AbstractPresenterModule {
	private static Logger logger = Logger.getLogger(ClientModule.class.getName());

	@Override
	protected void configure() {
		logger.info("ClientModule().configure(");

        install(new CoreModule());
		install(new LoginModule());
		install(new Oauth2RedirectModule());

		bind(UserManager.class).asEagerSingleton();
		bind(ResourceLoader.class).asEagerSingleton();
		bind(AdmServiceWorkerManager.class).asEagerSingleton();

		install(new AdmModule());

		install(new DashboardModule());

		install(new SystemConfigModule());
	}
}
