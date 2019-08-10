/**
 * 
 */
package io.crs.mws.client.app.spot;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author robi
 *
 */
public interface SpotMapUiHandlers extends UiHandlers {

	void createSpot(String name, Double coordinateX, Double coordinateY, Double coordinateZ);

}
