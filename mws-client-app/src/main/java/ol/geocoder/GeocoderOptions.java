/**
 * 
 */
package ol.geocoder;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import ol.Options;
import ol.control.ControlOptions;

/**
 * @author robi
 *
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class GeocoderOptions implements Options {

	@JsProperty
	public native void setProvider(String provider);

	@JsProperty
	public native void setLang(String lang);

	@JsProperty
	public native void setPlaceholder(String lang);

	@JsProperty
	public native void setLimit(int limit);

	@JsProperty
	public native void setDebug(boolean debug);

	@JsProperty
	public native void setAutoComplete(boolean autoComplete);

	@JsProperty
	public native void setKeepOpen(boolean keepOpen);

	@JsProperty
	public native void setCrs(String crs);

}
