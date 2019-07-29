/**
 * 
 */
package io.crs.mws.client.app.gin;

import javax.inject.Singleton;

import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.app.AppModule;
import io.crs.mws.client.app.auth.login.LoginModule;
import io.crs.mws.client.app.auth.oauth2redirect.Oauth2RedirectModule;
import io.crs.mws.client.app.auth.signup.SignupModule;
import io.crs.mws.client.app.dashboard.DashboardModule;
import io.crs.mws.client.core.gin.CoreModule;
import io.crs.mws.client.core.resources.ThemeParams;

/**
 * @author CR
 *
 */
public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindConstant().annotatedWith(DefaultPlace.class).to(io.crs.mws.client.core.NameTokens.HOME);
		bindConstant().annotatedWith(ErrorPlace.class).to(io.crs.mws.client.core.NameTokens.LOGIN);
		bindConstant().annotatedWith(UnauthorizedPlace.class).to(io.crs.mws.client.core.NameTokens.LOGIN);

		install(new CoreModule());

		bind(AppResourceLoader.class).asEagerSingleton();
		bind(ThemeParams.class).to(AppThemeParams.class).in(Singleton.class);

		install(new AppModule());
		install(new SignupModule());
		install(new LoginModule());
		install(new Oauth2RedirectModule());

		install(new DashboardModule());
	}
}
