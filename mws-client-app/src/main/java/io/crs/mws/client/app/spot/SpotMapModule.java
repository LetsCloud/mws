/**
 * 
 */
package io.crs.mws.client.app.spot;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author robi
 *
 */
public class SpotMapModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(SpotMapPresenter.class, SpotMapPresenter.MyView.class, SpotMapView.class,
				SpotMapPresenter.MyProxy.class);
	}
}
