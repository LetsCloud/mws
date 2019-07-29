package io.crs.mws.client.core.app;

import com.gwtplatform.mvp.client.UiHandlers;

import io.crs.mws.client.core.firebase.messaging.MessagingManager;

public interface AppUiHandlers extends UiHandlers {
	void logout();

	MessagingManager getMessagingManager();
}
