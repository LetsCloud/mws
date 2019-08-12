/**
 * 
 */
package io.crs.mws.client.core.security.oauth2redirect;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author robi
 *
 */
public class Oauth2RedirectModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(Oauth2RedirectPresenter.class, Oauth2RedirectPresenter.MyView.class, Oauth2RedirectView.class,
				Oauth2RedirectPresenter.MyProxy.class);
	}
}
