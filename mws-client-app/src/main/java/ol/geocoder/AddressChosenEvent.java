/**
 * 
 */
package ol.geocoder;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import ol.Coordinate;
import ol.PluggableMap;
import ol.events.Event;

/**
 * @author robi
 *
 */
@JsType(isNative = true)
public interface AddressChosenEvent extends Event {

    /**
     * The map where the event occurred.
     *
     * @return {@link ol.PluggableMap}
     */
    @JsProperty
    Coordinate getCoordinate();

}