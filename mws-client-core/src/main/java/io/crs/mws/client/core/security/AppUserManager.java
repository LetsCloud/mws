/**
 * 
 */
package io.crs.mws.client.core.security;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;

import io.crs.mws.client.core.firebase.messaging.MessagingManager;
import io.crs.mws.client.core.promise.xgwt.Fn;

/**
 * @author robi
 *
 */
public class AppUserManager extends UserManager {
	private final MessagingManager messagingManager;

	@Inject
	AppUserManager(EventBus eventBus, MessagingManager messagingManager, CurrentUser currentUser) {
		super(eventBus, currentUser);
		this.messagingManager = messagingManager;
	}
/*
	@Override
	protected void onSuccessAccountLoad(Fn.NoArg callback) {
			messagingManager.initFirebase(callback);
	}
*/
}
