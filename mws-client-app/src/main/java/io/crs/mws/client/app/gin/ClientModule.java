/**
 * 
 */
package io.crs.mws.client.app.gin;

import javax.inject.Singleton;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.app.AppModule;
import io.crs.mws.client.app.auth.login.LoginModule;
import io.crs.mws.client.app.auth.signup.SignupModule;
import io.crs.mws.client.app.auth.success.SuccessModule;
import io.crs.mws.client.app.dashboard.DashboardModule;
import io.crs.mws.client.app.spot.SpotMapModule;
import io.crs.mws.client.core.gin.CoreModule;
import io.crs.mws.client.core.resources.ThemeParams;
import io.crs.mws.client.core.security.oauth2redirect.Oauth2RedirectModule;

/**
 * @author CR
 *
 */
public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new CoreModule());

		bind(AppResourceLoader.class).asEagerSingleton();
		bind(ThemeParams.class).to(AppThemeParams.class).in(Singleton.class);

		install(new AppModule());
		install(new LoginModule());
		install(new Oauth2RedirectModule());
		install(new SignupModule());
		install(new SuccessModule());

		install(new DashboardModule());
		install(new SpotMapModule());
	}
}
