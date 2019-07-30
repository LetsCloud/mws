/**
 * 
 */
package io.crs.mws.client.app.spot;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import io.crs.mws.client.app.NameTokens;
import io.crs.mws.client.core.app.AbstractAppPresenter;
import io.crs.mws.client.core.event.ContentPushEvent;
import io.crs.mws.client.core.event.SetPageTitleEvent;
import io.crs.mws.client.core.security.LoggedInGatekeeper;
import io.crs.mws.shared.cnst.MenuItemType;

/**
 * @author robi
 *
 */
public class SpotMapPresenter extends Presenter<SpotMapPresenter.MyView, SpotMapPresenter.MyProxy>
		implements SpotMapUiHandlers, ContentPushEvent.ContentPushHandler {
	private static Logger logger = Logger.getLogger(SpotMapPresenter.class.getName());

	interface MyView extends View, HasUiHandlers<SpotMapUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.SPOTS)
	@UseGatekeeper(LoggedInGatekeeper.class)
	interface MyProxy extends ProxyPlace<SpotMapPresenter> {
	}

	@Inject
	SpotMapPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, AbstractAppPresenter.SLOT_MAIN);
		logger.log(Level.INFO, "SpotMapPresenter()");

		addRegisteredHandler(ContentPushEvent.TYPE, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		logger.log(Level.INFO, "SpotMapPresenter().onBind()");
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		logger.log(Level.INFO, "SpotMapPresenter().onReveal()");
		SetPageTitleEvent.fire("Spots", "", MenuItemType.MENU_ITEM, this);
	}

	@Override
	public void onContentPush(ContentPushEvent event) {
// TODO Auto-generated method stub

	}

}
