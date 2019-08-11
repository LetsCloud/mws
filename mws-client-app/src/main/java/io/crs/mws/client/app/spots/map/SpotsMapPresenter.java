/**
 * 
 */
package io.crs.mws.client.app.spots.map;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import io.crs.mws.client.core.service.WindspotService;
import io.crs.mws.shared.dto.WindspotDto;

/**
 * @author robi
 *
 */
public class SpotsMapPresenter extends PresenterWidget<SpotsMapPresenter.MyView> implements SpotsMapUiHandlers {
	private static Logger logger = Logger.getLogger(SpotsMapPresenter.class.getName());

	public static final SingleSlot<PresenterWidget<?>> EDITOR_SLOT = new SingleSlot<>();

	private static final WindspotService WINDSPOT_SERVICE = GWT.create(WindspotService.class);

	public interface MyView extends View, HasUiHandlers<SpotsMapUiHandlers> {
		void reveal(List<WindspotDto> windspots);

		void refresh();
	}

	private final PlaceManager placeManager;

	@Inject
	SpotsMapPresenter(EventBus eventBus, PlaceManager placeManager, MyView view) {
		super(eventBus, view);
		logger.info("SpotsMapPresenter()");

		this.placeManager = placeManager;

		getView().setUiHandlers(this);
	}

	@Override
	public void loadData() {
		WINDSPOT_SERVICE.getAll(new MethodCallback<List<WindspotDto>>() {

			@Override
			public void onSuccess(Method method, List<WindspotDto> response) {
				getView().reveal(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
			}
		});
	}

	@Override
	public void createSpot(String name, Double coordinateX, Double coordinateY, Double coordinateZ) {
		WINDSPOT_SERVICE.createOrSave(
				new WindspotDto(name, coordinateX.toString(), coordinateY.toString(), coordinateZ.toString()),
				new MethodCallback<WindspotDto>() {

					@Override
					public void onSuccess(Method method, WindspotDto response) {
					}

					@Override
					public void onFailure(Method method, Throwable exception) {
					}
				});
	}
}