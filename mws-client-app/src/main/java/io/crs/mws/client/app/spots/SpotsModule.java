/**
 * 
 */
package io.crs.mws.client.app.spots;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.app.spots.list.SpotsListModule;
import io.crs.mws.client.app.spots.map.SpotsMapModule;

/**
 * @author robi
 *
 */
public class SpotsModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		install(new SpotsListModule());
		install(new SpotsMapModule());

		bindPresenter(SpotsPresenter.class, SpotsPresenter.MyView.class, SpotsView.class, SpotsPresenter.MyProxy.class);
	}
}
