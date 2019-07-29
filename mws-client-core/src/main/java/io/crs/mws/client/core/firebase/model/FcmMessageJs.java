/**
 * 
 */
package io.crs.mws.client.core.firebase.model;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * @author robi
 *
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class FcmMessageJs {

	@JsProperty
	public String collapse_key;

	@JsProperty
	public FcmDataJs data;

	@JsProperty
	public String from;

	@JsProperty
	public FcmNotificationJs notification;

	@JsOverlay
	public final String getCollapse_key() {
		return collapse_key;
	}

	@JsOverlay
	public final FcmDataJs getData() {
		return data;
	}

	@JsOverlay
	public final String getFrom() {
		return from;
	}

	@JsOverlay
	public final FcmNotificationJs getNotification() {
		return notification;
	}

	@Override
	@JsMethod
	public native String toString();
}
