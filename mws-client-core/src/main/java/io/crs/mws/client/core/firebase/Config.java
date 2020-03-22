/**
 * 
 */
package io.crs.mws.client.core.firebase;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * @author robi
 *
 */
@JsType(isNative = false)
public class Config {
	@JsProperty
	public native void setApiKey(String apiKey);

	@JsProperty
	public native String getApiKey();

	@JsProperty
	public native void setAuthDomain(String authDomain);

	@JsProperty
	public native String getAuthDomain();

	@JsProperty
	public native void setProjectId(String projectId);

	@JsProperty
	public native String getProjectId();

	@JsProperty
	public native void setDatabaseURL(String databaseURL);

	@JsProperty
	public native String getDatabaseURL();

	@JsProperty
	public native void setStorageBucket(String storageBucket);

	@JsProperty
	public native String getStorageBucket();

	@JsProperty
	public native void setMessagingSenderId(String messagingSenderId);

	@JsProperty
	public native String getMessagingSenderId();

	@JsProperty
	public native void setAppId(String appId);

	@JsProperty
	public native String getAppId();
}
