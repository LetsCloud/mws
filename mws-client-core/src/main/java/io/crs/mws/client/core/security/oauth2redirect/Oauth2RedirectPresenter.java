/**
 * 
 */
package io.crs.mws.client.core.security.oauth2redirect;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
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
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import io.crs.mws.client.core.CoreNameTokens;
import io.crs.mws.client.core.firebase.messaging.MessagingManager;
import io.crs.mws.client.core.i18n.CoreMessages;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.security.UserManager;
import io.crs.mws.client.core.service.AuthService;
import io.crs.mws.client.core.util.OauthUtils;
import io.crs.mws.shared.cnst.Language;
import io.crs.mws.shared.dto.AccountDto;

/**
 * @author robi
 *
 */
public class Oauth2RedirectPresenter extends Presenter<Oauth2RedirectPresenter.MyView, Oauth2RedirectPresenter.MyProxy>
		implements Oauth2RedirectUiHandlers {
	private static Logger logger = Logger.getLogger(Oauth2RedirectPresenter.class.getName());

	interface MyView extends View, HasUiHandlers<Oauth2RedirectUiHandlers> {
		void setEmail(String text);
	}

	@NameToken(CoreNameTokens.OAUTH2REDIRECT)
	@ProxyStandard
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<Oauth2RedirectPresenter> {
	}

	private static final AuthService AUTH_SERVICE = GWT.create(AuthService.class);

	private String placeToGo;
	private Map<String, String> placeParams = new HashMap<String, String>();

	private final PlaceManager placeManager;
	private final UserManager userManager;
	private final MessagingManager messagingManager;
	private final AppData appData;
	private final CurrentUser currentUser;
	private final CoreMessages i18n;

	@Inject
	Oauth2RedirectPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, AppData appData,
			CurrentUser currentUser, CoreMessages i18n, MessagingManager messagingManager, UserManager userManager) {
		super(eventBus, view, proxy, RevealType.Root);
		logger.info("Oauth2RedirectPresenter()");

		this.placeManager = placeManager;
		this.userManager = userManager;
		this.messagingManager = messagingManager;
		this.appData = appData;
		this.currentUser = currentUser;
		this.i18n = i18n;

		getView().setUiHandlers(this);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		logger.info("Oauth2RedirectPresenter().prepareFromRequest()");
//		String requestToken = request.getParameter(LoggedInGatekeeper.PLACE_TO_GO, null);
		String requestToken = request.getParameter("token", null);
		logger.info("Oauth2RedirectPresenter().prepareFromRequest()->requestToken=" + requestToken);

		OauthUtils.storeAccessToken(requestToken);

		userManager.load(() -> {
			logger.info("Oauth2RedirectPresenter().prepareFromRequest()-2");
			placeManager.revealDefaultPlace();
			logger.info("Oauth2RedirectPresenter().prepareFromRequest()-3");
			});
		/*
		 * if (Strings.isNullOrEmpty(requestToken)) { return; }
		 * 
		 * Integer paramStart = requestToken.indexOf("?"); if (paramStart == -1) {
		 * placeToGo = requestToken; } else { placeToGo = requestToken.substring(0,
		 * paramStart); String requestParams = requestToken.substring(paramStart + 1);
		 * Integer equalSign = requestParams.indexOf("=");
		 * placeParams.put(requestParams.substring(0, equalSign),
		 * requestParams.substring(equalSign + 1)); }
		 */
		getProxy().manualReveal(Oauth2RedirectPresenter.this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		logger.info("Oauth2RedirectPresenter().onBind()");
	}

	@Override
	public boolean useManualReveal() {
		logger.info("Oauth2RedirectPresenter().useManualReveal()");
		return true;
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		logger.info("Oauth2RedirectPresenter().onReveal()");

		@SuppressWarnings("deprecation")
		com.google.gwt.user.client.Element splash = DOM.getElementById("splashscreen");
		if (splash != null)
			splash.removeFromParent();

		AUTH_SERVICE.getCurrentUser(new MethodCallback<AccountDto>() {

			@Override
			public void onSuccess(Method method, AccountDto response) {
				logger.info("checkCurrentUser().onSuccess()");
				if (response == null) {
					logger.info("checkCurrentUser().onSuccess()->(result == null)");
					currentUser.setLoggedIn(false);
					return;
				}
				String locale = LocaleInfo.getCurrentLocale().getLocaleName();

				currentUser.setLocale(locale);
				currentUser.setLanguage(Language.en);
				if (locale.startsWith("uk"))
					currentUser.setLanguage(Language.uk);
				if (locale.startsWith("hu"))
					currentUser.setLanguage(Language.hu);

				currentUser.setAccountDto(response);
				currentUser.setLoggedIn(true);
				getView().setEmail(currentUser.getAccountDto().getEmail());
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.info("AbstractAppPresenter().checkCurrentUser().onFailure()->caught.getMessage()="
						+ exception.getMessage());

				currentUser.setLoggedIn(false);
			}
		});

	}
}