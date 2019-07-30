/**
 * 
 */
package io.crs.mws.client.app.spot;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author robi
 *
 */
public class SpotMapView extends ViewWithUiHandlers<SpotMapUiHandlers> implements SpotMapPresenter.MyView {
	private static Logger logger = Logger.getLogger(SpotMapView.class.getName());

	interface Binder extends UiBinder<Widget, SpotMapView> {
	}

	@Inject
	SpotMapView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		logger.log(Level.INFO, "SpotMapView");
	}
}
