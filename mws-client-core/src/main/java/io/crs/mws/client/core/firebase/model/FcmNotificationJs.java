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
public class FcmNotificationJs {

	@JsProperty
	public String icon;

	@JsProperty
	public String title;

	@JsProperty
	public String body;

	@JsOverlay
	public final String getIcon() {
		return icon;
	}

	@JsOverlay
	public final String getTitle() {
		return title;
	}

	@JsOverlay
	public final String getBody() {
		return body;
	}

	@Override
	@JsMethod
	public native String toString();
}
