/**
 * 
 */
package io.crs.mws.client.app.auth.signup;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author robi
 *
 */
public class SignupModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(SignupPresenter.class, SignupPresenter.MyView.class, SignupView.class,
				SignupPresenter.MyProxy.class);
	}
}
