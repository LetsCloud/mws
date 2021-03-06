package io.crs.mws.client.app.security;

import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import io.crs.mws.client.app.NameTokens;
import io.crs.mws.client.core.CoreNameTokens;
import io.crs.mws.client.core.security.CurrentUser;

@DefaultGatekeeper
public class LoggedInGatekeeper implements Gatekeeper {
	private static Logger logger = Logger.getLogger(LoggedInGatekeeper.class.getName());

	private final PlaceManager placeManager;
	private final CurrentUser currentUser;

	@Inject
	LoggedInGatekeeper(PlaceManager placeManager, CurrentUser currentUser) {
		logger.info("LoggedInGatekeeper()");
		this.placeManager = placeManager;
		this.currentUser = currentUser;
	}

	@Override
	public boolean canReveal() {
		logger.info("LoggedInGatekeeper().canReveal()");
		if (!currentUser.isLoggedIn())
			goToLogin();
		return currentUser.isLoggedIn();
	}

	private void goToLogin() {
		logger.info("LoggedInGatekeeper().goToLogin()");
		StringBuilder sb = new StringBuilder();
		sb.append(placeManager.getCurrentPlaceRequest().getNameToken());
		
		Set<String> params = placeManager.getCurrentPlaceRequest().getParameterNames();
		for (String param : params) {
			sb.append("?");
			sb.append(param);
			sb.append("=");
			sb.append(placeManager.getCurrentPlaceRequest().getParameter(param, null));
		}
		
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(CoreNameTokens.LOGIN)
				.with(CoreNameTokens.PLACE_TOGO, sb.toString()).build();
		placeManager.revealPlace(placeRequest);
	}

}
