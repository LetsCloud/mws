package io.crs.mws.client.app;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.core.app.AppView;

public class AppModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(AppPresenter.class, AppPresenter.MyView.class, AppView.class,
				AppPresenter.MyProxy.class);
	}
}
