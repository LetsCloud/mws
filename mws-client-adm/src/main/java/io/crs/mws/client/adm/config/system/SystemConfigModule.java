/**
 * 
 */
package io.crs.mws.client.adm.config.system;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.adm.browser.globalconfig.GlobalConfigBrowserModule;

/**
 * @author robi
 *
 */
public class SystemConfigModule extends AbstractPresenterModule {
	@Override
	protected void configure() {

		install(new GlobalConfigBrowserModule());

		bindPresenter(SystemConfigPresenter.class, SystemConfigPresenter.MyView.class, SystemConfigView.class,
				SystemConfigPresenter.MyProxy.class);
	}
}
