/**
 * 
 */
package io.crs.mws.client.core.security.login;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import io.crs.mws.client.core.CoreNameTokens;
import io.crs.mws.client.core.i18n.CoreMessages;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.shared.dto.EntityPropertyCode;
import io.crs.mws.shared.dto.auth.LoginRequest;

/**
 * @author robi
 *
 */
public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy>
		implements LoginUiHandlers {
	private static Logger logger = Logger.getLogger(LoginPresenter.class.getName());

	public interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
		void setPlaceToGo(String placeTogo, LoginRequest loginRequest);

		void displayError(EntityPropertyCode code, String message);
	}

	@NameToken(CoreNameTokens.LOGIN)
	@ProxyStandard
	@NoGatekeeper
	public interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	private String placeToGo;
	private Map<String, String> placeParams = new HashMap<String, String>();

	private final PlaceManager placeManager;
	private final UserManager userManager;
	private final CoreMessages i18n;

	@Inject
	LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, UserManager userManager,
			CoreMessages i18n) {
		super(eventBus, view, proxy, RevealType.Root);
		logger.info("LoginPresenter()");

		this.placeManager = placeManager;
		this.userManager = userManager;
		this.i18n = i18n;

		getView().setUiHandlers(this);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		logger.info("LoginPresenter().prepareFromRequest()->nameToken=" + this.getProxy().getNameToken());
		String requestToken = request.getParameter(CoreNameTokens.PLACE_TOGO, null);
		if (Strings.isNullOrEmpty(requestToken)) {
			logger.info("LoginPresenter().prepareFromRequest()->isNullOrEmpty(requestToken)");
			checkCurentUser();
			return;
		}

		Integer paramStart = requestToken.indexOf("?");
		if (paramStart == -1) {
			placeToGo = requestToken;
		} else {
			placeToGo = requestToken.substring(0, paramStart);
			String requestParams = requestToken.substring(paramStart + 1);
			Integer equalSign = requestParams.indexOf("=");
			placeParams.put(requestParams.substring(0, equalSign), requestParams.substring(equalSign + 1));
		}
		logger.info("LoginPresenter().prepareFromRequest()->placeToGo=" + placeToGo);
		checkCurentUser();
	}

	@Override
	public boolean useManualReveal() {
		logger.info("LoginPresenter().useManualReveal()");
		return true;
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		logger.info("LoginPresenter().onReveal()->placeToGo=" + placeToGo);

		@SuppressWarnings("deprecation")
		com.google.gwt.user.client.Element splash = DOM.getElementById("splashscreen");
		if (splash != null)
			splash.removeFromParent();

		getView().setPlaceToGo(placeToGo, new LoginRequest());
	}

	/**
	* 
	*/
	private void checkCurentUser() {
		logger.info("LoginPresenter().checkCurentUser()");
		Timer t2 = new Timer() {
			@Override
			public void run() {
				getProxy().manualReveal(LoginPresenter.this);
			}
		};
		t2.schedule(100);
	}

	@Override
	public void login(LoginRequest loginRequest) {
		userManager.login(loginRequest, () -> {
			logger.info("LoginPresenter().login().callback()->placeToGo=" + placeToGo);
			goToPlace(placeToGo);
		}, () -> {
			getView().displayError(null, i18n.loginFaildSignIn());
		});
	}

	/**
	 * 
	 * @param place
	 */
	private void goToPlace(String place) {
		PlaceRequest.Builder placeRequest = new PlaceRequest.Builder().nameToken(place);
		for (Map.Entry<String, String> entry : placeParams.entrySet()) {
			logger.info("LoginPresenter().goToPlace()->paramName=" + entry.getKey());
			logger.info("LoginPresenter().goToPlace()->paramValue=" + entry.getValue());
			placeRequest.with(entry.getKey(), entry.getValue());
		}
		placeManager.revealPlace(placeRequest.build());
	}
}