/**
 * 
 */
package io.crs.mws.client.core.firebase.messaging.js;

import gwt.material.design.client.pwa.serviceworker.js.ServiceWorkerRegistration;
import io.crs.mws.client.core.promise.xgwt.Promise;
import jsinterop.annotations.JsType;

/**
 * firebase.messaging.Messaging
 * 
 * The Firebase Messaging service interface. Do not call this constructor
 * directly. Instead, use firebase.messaging().
 * 
 * @author robi
 *
 */
@JsType(isNative = true, namespace = "firebase.messaging", name = "Messaging")
public class Messaging {

	/**
	 * deleteToken
	 * 
	 * To forceably stop a registration token from being used, delete it by calling
	 * this method. Calling this method will stop the periodic data transmission to
	 * the FCM backend.
	 * 
	 * @param token string The token to delete. Value must not be null.
	 * @return firebase.Promise The promise resolves when the token has been
	 *         successfully deleted.
	 */
	public native Promise<Void, Error> deleteToken(String token);

	/**
	 * getToken
	 * 
	 * After calling requestPermission() you can call this method to get an FCM
	 * registration token that can be used to send push messages to this user. The
	 * generated registration token is used to identify the web app instance and
	 * periodically sends data to the Firebase backend. To stop this, call
	 * firebase.messaging.Messaging#deleteToken.
	 * 
	 * @return firebase.Promise containing string The promise resolves if an FCM
	 *         token can be retrieved. This method returns null if the current
	 *         origin does not have permission to show notifications.
	 */
	public native Promise<String, Error> getToken();

	/**
	 * onMessage
	 * 
	 * When a push message is received and the user is currently on a page for your
	 * origin, the message is passed to the page and an onMessage() event is
	 * dispatched with the payload of the push message.
	 * 
	 * NOTE: These events are dispatched when you have called
	 * setBackgroundMessageHandler() in your service worker.
	 * 
	 * @param nextOrObserver (non-null function(non-null Object) or non-null Object)
	 *                       This function, or observer object with next defined, is
	 *                       called when a message is received and the user is
	 *                       currently viewing your page.
	 * @return function() To stop listening for messages execute this returned
	 *         function.
	 */
	public native Fnx.NoArg onMessage(Fnx.Arg nextOrObserver);

	/**
	 * onTokenRefresh
	 * 
	 * You should listen for token refreshes so your web app knows when FCM has
	 * invalidated your existing token and you need to call getToken() to get a new
	 * token.
	 * 
	 * @param nextOrObserver (non-null function(non-null Object) or non-null Object)
	 *                       This function, or observer object with next defined, is
	 *                       called when a token refresh has occurred.
	 * @return function() To stop listening for token refresh events execute this
	 *         returned function.
	 */
	public native Fnx.NoArg onTokenRefresh(Fnx.NoArg nextOrObserver);

	/**
	 * requestPermission
	 * 
	 * Notification permissions are required to send a user push messages. Calling
	 * this method displays the permission dialog to the user and resolves if the
	 * permission is granted.
	 * 
	 * @return firebase.Promise The promise resolves if permission is granted.
	 *         Otherwise, the promise is rejected with an error.
	 */
	public native Promise<Void, Error> requestPermission();

	/**
	 * setBackgroundMessageHandler
	 * 
	 * FCM directs push messages to your web page's onMessage() callback if the user
	 * currently has it open. Otherwise, it calls your callback passed into
	 * setBackgroundMessageHandler().
	 * 
	 * Your callback should return a promise that, once resolved, has shown a
	 * notification.
	 * 
	 * @param callback
	 */
	public native void setBackgroundMessageHandler(Fnx.NoArg callback);

	/**
	 * usePublicVapidKey
	 * 
	 * To set your own server application key, you can specify here the public key
	 * you set up from the Firebase Console under the Settings options.
	 * 
	 * @param publicKey A URL safe base64 encoded server application key. Value must
	 *                  not be null.
	 */
	public native void usePublicVapidKey(String publicKey);

	/**
	 * useServiceWorker
	 * 
	 * To use your own service worker for receiving push messages, you can pass in
	 * your service worker registration in this method.
	 * 
	 * @param registration The service worker registration you wish to use for push
	 *                     messaging.
	 * 
	 *                     Value must not be null.
	 */
	public native void useServiceWorker(ServiceWorkerRegistration registration);

}
