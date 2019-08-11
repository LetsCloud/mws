/**
 * 
 */
package io.crs.mws.client.app.spots.map;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author robi
 *
 */
public interface SpotsMapUiHandlers extends UiHandlers {

	void loadData();

	void createSpot(String name, Double coordinateX, Double coordinateY, Double coordinateZ);

}
