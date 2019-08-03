/**
 * 
 */
package ol.popup;

import com.google.gwt.dom.client.Element;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import ol.OverlayOptions;

/**
 * @author robi
 *
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class PopupOptions extends OverlayOptions {

	@JsProperty
	public native void setElement(Element element);

}
