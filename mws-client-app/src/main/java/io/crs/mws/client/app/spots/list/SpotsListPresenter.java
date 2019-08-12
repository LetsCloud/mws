/**
 * 
 */
package io.crs.mws.client.app.spots.list;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import io.crs.mws.shared.dto.WindspotDto;

/**
 * @author robi
 *
 */
public class SpotsListPresenter extends PresenterWidget<SpotsListPresenter.MyView> implements SpotsListUiHandlers {
	private static Logger logger = Logger.getLogger(SpotsListPresenter.class.getName());

	public static final SingleSlot<PresenterWidget<?>> EDITOR_SLOT = new SingleSlot<>();

	public interface MyView extends View, HasUiHandlers<SpotsListUiHandlers> {
		void setData(List<WindspotDto> data, String chatWebSafeKey);

		void refreshPanel(Boolean showEditor);
	}

	private final PlaceManager placeManager;

	@Inject
	SpotsListPresenter(EventBus eventBus, PlaceManager placeManager, MyView view) {
		super(eventBus, view);
		logger.info("ChatListPresenter()");

		this.placeManager = placeManager;

		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}