/**
 * 
 */
package io.crs.mws.client.adm.login;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.core.security.login.LoginPresenter;

/**
 * @author CR
 *
 */
public class LoginModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class, LoginView.class,
                LoginPresenter.MyProxy.class);
    }
}
