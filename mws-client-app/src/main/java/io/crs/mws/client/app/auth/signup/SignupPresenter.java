/**
 * 
 */
package io.crs.mws.client.app.auth.signup;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import gwt.material.design.client.ui.MaterialToast;
import io.crs.mws.client.app.NameTokens;
import io.crs.mws.client.app.i18n.AppConstants;
import io.crs.mws.client.core.i18n.CoreMessages;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.service.AuthService;
import io.crs.mws.shared.dto.auth.SignupResponse;
import io.crs.mws.shared.dto.auth.SignUpRequest;

/**
 * @author robi
 *
 */
public class SignupPresenter extends Presenter<SignupPresenter.MyView, SignupPresenter.MyProxy>
		implements SignupUiHandlers {
	private static Logger logger = Logger.getLogger(SignupPresenter.class.getName());

	private static final AuthService AUTH_SERVICE = GWT.create(AuthService.class);

	interface MyView extends View, HasUiHandlers<SignupUiHandlers> {
		void start(SignUpRequest signUpRequest);
	}

	@NameToken(NameTokens.SIGNUP)
	@ProxyStandard
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<SignupPresenter> {
	}

	private final PlaceManager placeManager;
	private final CoreMessages i18n;
	private final AppConstants cnst;

	@Inject
	SignupPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, AppData appData,
			CoreMessages i18n, AppConstants cnst) {
		super(eventBus, view, proxy, RevealType.Root);
		logger.info("SignupPresenter()");

		this.placeManager = placeManager;
		this.i18n = i18n;
		this.cnst = cnst;

		getView().setUiHandlers(this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		@SuppressWarnings("deprecation")
		com.google.gwt.user.client.Element splash = DOM.getElementById("splashscreen");
		if (splash != null)
			splash.removeFromParent();

		getView().start(new SignUpRequest());
	}

	@Override
	public void signUp(SignUpRequest signUpRequest) {
		AUTH_SERVICE.signUp(signUpRequest, new MethodCallback<SignupResponse>() {

			@Override
			public void onSuccess(Method method, SignupResponse response) {
				if (response.isSuccess()) {
				} else {
					String msg = cnst.signupErrorMap().get(response.getErrorCode().toString());
					if (Strings.isNullOrEmpty(msg))
						msg = "Unknown error!";
					MaterialToast.fireToast(msg, "toastError");
				}
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.info("SignupPresenter().signUp()->exception=" + exception.getMessage() + " / "
						+ exception.getCause().getMessage());
			}
		});
	}

}
