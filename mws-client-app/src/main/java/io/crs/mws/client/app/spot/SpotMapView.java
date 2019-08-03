/**
 * 
 */
package io.crs.mws.client.app.spot;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import io.crs.mws.client.app.spot.example.OLExampleType;

/**
 * @author robi
 *
 */
public class SpotMapView extends ViewWithUiHandlers<SpotMapUiHandlers> implements SpotMapPresenter.MyView {
	private static Logger logger = Logger.getLogger(SpotMapView.class.getName());

	interface Binder extends UiBinder<Widget, SpotMapView> {
	}

	@UiField
	SimplePanel panel;

	@Inject
	SpotMapView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		logger.log(Level.INFO, "SpotMapView");
	}

	@Override
	public void start() {

		OLExampleType example = OLExampleType.valueOf("MarkerExample");

		panel.setSize("100%", "100%");
		panel.getElement().setId(example.getExample().toString());

//		Scheduler.get().scheduleDeferred(() -> example.getExample().show(example.getExample().toString()));

		panel.setVisible(true);

		Timer t = new Timer() {
			@Override
			public void run() {
				logger.info("SpotMapView().run->start");
				example.getExample().show(example.getExample().toString());
				logger.info("SpotMapView().run->end");
			}
		};
		t.schedule(100);

	}
}
