package io.crs.mws.client.adm;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.core.app.AppView;

public class AdmModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(AdmPresenter.class, AdmPresenter.MyView.class, AppView.class,
				AdmPresenter.MyProxy.class);
	}
}
