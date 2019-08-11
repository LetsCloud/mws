/**
 * 
 */
package io.crs.mws.client.app.spots.map;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author robi
 *
 */
public class SpotsMapModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenterWidget(SpotsMapPresenter.class, SpotsMapPresenter.MyView.class, SpotsMapView.class);

		install(new GinFactoryModuleBuilder().build(SpotsMapFactory.class));
	}
}
