/**
 * 
 */
package io.crs.mws.client.app.spots;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import io.crs.mws.client.app.NameTokens;
import io.crs.mws.client.app.security.LoggedInGatekeeper;
import io.crs.mws.client.app.spots.map.SpotsMapFactory;
import io.crs.mws.client.app.spots.map.SpotsMapPresenter;
import io.crs.mws.client.core.app.AbstractAppPresenter;
import io.crs.mws.client.core.event.ContentPushEvent;
import io.crs.mws.client.core.event.SetPageTitleEvent;
import io.crs.mws.shared.cnst.MenuItemType;
import io.crs.mws.shared.dto.WindspotDto;

/**
 * @author robi
 *
 */
public class SpotsPresenter extends Presenter<SpotsPresenter.MyView, SpotsPresenter.MyProxy>
		implements SpotsUiHandlers, ContentPushEvent.ContentPushHandler {
	private static Logger logger = Logger.getLogger(SpotsPresenter.class.getName());

	public static final SingleSlot<PresenterWidget<?>> SLOT_MAP = new SingleSlot<>();
	public static final SingleSlot<PresenterWidget<?>> SLOT_LIST = new SingleSlot<>();

	interface MyView extends View, HasUiHandlers<SpotsUiHandlers> {
		void reveal(List<WindspotDto> windpots);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.SPOTS)
	@UseGatekeeper(LoggedInGatekeeper.class)
	interface MyProxy extends ProxyPlace<SpotsPresenter> {
	}

	private final SpotsMapPresenter spotsMapPresenter;

	@Inject
	SpotsPresenter(EventBus eventBus, MyView view, MyProxy proxy, SpotsMapFactory spotsMapFactory) {
		super(eventBus, view, proxy, AbstractAppPresenter.SLOT_MAIN);
		logger.log(Level.INFO, "SpotMapPresenter()");

		this.spotsMapPresenter = spotsMapFactory.createSpotsMapPresenter();

		getView().setUiHandlers(this);

		addRegisteredHandler(ContentPushEvent.TYPE, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		logger.log(Level.INFO, "SpotMapPresenter().onBind()");

		setInSlot(SLOT_MAP, spotsMapPresenter);
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		logger.log(Level.INFO, "SpotMapPresenter().onReveal()");
		SetPageTitleEvent.fire("Spots", "", MenuItemType.MENU_ITEM, this);

		spotsMapPresenter.loadData();
	}

	@Override
	public void onContentPush(ContentPushEvent event) {
		logger.info("SpotMapPresenter.onContentPush()");
	}

}