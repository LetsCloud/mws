/**
 * 
 */
package io.crs.mws.client.app.auth.success;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import io.crs.mws.client.app.NameTokens;
import io.crs.mws.client.core.i18n.CoreMessages;
import io.crs.mws.client.core.security.AppData;

/**
 * @author CR
 *
 */
public class SuccessPresenter extends Presenter<SuccessPresenter.MyView, SuccessPresenter.MyProxy>
		implements SuccessUiHandlers {
	private static Logger logger = Logger.getLogger(SuccessPresenter.class.getName());

	interface MyView extends View, HasUiHandlers<SuccessUiHandlers> {
	}

	@NameToken(NameTokens.SUCCESS)
	@ProxyStandard
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<SuccessPresenter> {
	}

	private final PlaceManager placeManager;
	private final CoreMessages i18n;

	@Inject
	SuccessPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, AppData appData,
			CoreMessages i18n) {
		super(eventBus, view, proxy, RevealType.Root);
		logger.info("SuccessPresenter()");

		this.placeManager = placeManager;
		this.i18n = i18n;

		getView().setUiHandlers(this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();
	}
}
