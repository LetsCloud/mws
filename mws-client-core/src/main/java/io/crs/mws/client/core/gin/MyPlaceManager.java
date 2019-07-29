/**
 * 
 */
package io.crs.mws.client.core.gin;

import javax.inject.Inject;

import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.TokenFormatter;

/**
 * @author robi
 *
 */
public class MyPlaceManager extends PlaceManagerImpl {
	private final PlaceRequest defaultPlaceRequest;
	private final PlaceRequest errorPlaceRequest;
	private final PlaceRequest unauthorizedPlaceRequest;

	@Inject
	public MyPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace String defaultPlaceNameToken,
			@ErrorPlace String errorPlaceNameToken, @UnauthorizedPlace String unauthorizedPlaceNameToken) {
		super(eventBus, tokenFormatter, new Html5Historian());

		defaultPlaceRequest = new PlaceRequest.Builder().nameToken(defaultPlaceNameToken).build();
		errorPlaceRequest = new PlaceRequest.Builder().nameToken(errorPlaceNameToken).build();
		unauthorizedPlaceRequest = new PlaceRequest.Builder().nameToken(unauthorizedPlaceNameToken).build();
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest, false);
	}

	@Override
	public void revealErrorPlace(String invalidHistoryToken) {
		revealPlace(errorPlaceRequest, false);
	}

	@Override
	public void revealUnauthorizedPlace(String unauthorizedHistoryToken) {
		revealPlace(unauthorizedPlaceRequest, false);
	}
}