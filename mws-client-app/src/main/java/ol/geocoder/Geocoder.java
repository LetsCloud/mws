/**
 * 
 */
package ol.geocoder;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import ol.control.Control;

/**
 * @author robi
 *
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Geocoder")
public class Geocoder extends Control {

	public Geocoder() {
	}

	public Geocoder(String controlType, GeocoderOptions geocoderOptions) {
	}

}
