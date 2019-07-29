/**
 * 
 */
package io.crs.mws.client.core.firebase.messaging;

import gwt.material.design.client.pwa.serviceworker.js.ServiceWorkerRegistration;
import io.crs.mws.client.core.firebase.messaging.js.Fnx;
import io.crs.mws.client.core.firebase.messaging.js.Messaging;
import io.crs.mws.client.core.promise.xgwt.Fn;

/**
 * @author robi
 *
 */
public interface HasMessagingFeatures {
	Messaging getFirebaseMessaging();

	void getToken(Fn.Arg<String> callback);
	void onTokenRefresh(Fn.Arg<String> callback);
	void onMessage(Fnx.Arg callback);
	void requestPermission(Fn.NoArg callback);
	void useServiceWorker(ServiceWorkerRegistration r);
}
