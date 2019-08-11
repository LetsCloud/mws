/**
 * 
 */
package io.crs.mws.client.app.spots.list;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author robi
 *
 */
public class SpotsListModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenterWidget(SpotsListPresenter.class, SpotsListPresenter.MyView.class, SpotsListView.class);

		install(new GinFactoryModuleBuilder().build(SpotsListFactory.class));
	}
}
