/**
 * 
 */
package io.crs.mws.client.adm.dashboard;

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

import io.crs.mws.client.adm.LoggedInGatekeeper;
import io.crs.mws.client.core.CoreNameTokens;
import io.crs.mws.client.core.app.AbstractAppPresenter;
import io.crs.mws.client.core.event.ContentPushEvent;
import io.crs.mws.client.core.event.SetPageTitleEvent;
import io.crs.mws.shared.cnst.MenuItemType;

/**
 * @author CR
 *
 */
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy>
		implements DashboardUiHandlers, ContentPushEvent.ContentPushHandler {
	private static Logger logger = Logger.getLogger(DashboardPresenter.class.getName());

	interface MyView extends View, HasUiHandlers<DashboardUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(CoreNameTokens.HOME)
	@UseGatekeeper(LoggedInGatekeeper.class)
	interface MyProxy extends ProxyPlace<DashboardPresenter> {
	}

	@Inject
	DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, AbstractAppPresenter.SLOT_MAIN);
		logger.log(Level.INFO, "DashboardPresenter()");

		addRegisteredHandler(ContentPushEvent.TYPE, this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		SetPageTitleEvent.fire("Dashboard", "", MenuItemType.MENU_ITEM, this);
	}

	@Override
	public void onContentPush(ContentPushEvent event) {
		// TODO Auto-generated method stub

	}

}
