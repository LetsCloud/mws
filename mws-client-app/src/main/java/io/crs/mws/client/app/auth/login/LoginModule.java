/**
 * 
 */
package io.crs.mws.client.app.auth.login;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.core.security.login.LoginPresenter;

/**
 * @author robi
 *
 */
public class LoginModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class, LoginView.class,
                LoginPresenter.MyProxy.class);
    }
}
