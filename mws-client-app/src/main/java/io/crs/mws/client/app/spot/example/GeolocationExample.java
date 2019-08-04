/**
 * 
 */
package io.crs.mws.client.app.spot.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.Window;

import io.crs.mws.client.app.spot.OpenLayersConstants;
import io.crs.mws.client.app.spot.OpenLayersUtils;
import ol.Coordinate;
import ol.Feature;
import ol.Geolocation;
import ol.GeolocationOptions;
import ol.Map;
import ol.MapOptions;
import ol.OLFactory;
import ol.PositionOptions;
import ol.Size;
import ol.View;
import ol.ViewFitOptions;
import ol.ViewOptions;
import ol.control.Attribution;
import ol.control.ScaleLine;
import ol.events.Event;
import ol.geom.Point;
import ol.interaction.KeyboardPan;
import ol.interaction.KeyboardZoom;
import ol.layer.LayerOptions;
import ol.layer.Tile;
import ol.layer.Vector;
import ol.proj.Projection;
import ol.source.Osm;
import ol.source.XyzOptions;

/**
 * @author robi
 *
 */
public class GeolocationExample implements Example {
	private static Logger logger = Logger.getLogger(GeolocationExample.class.getName());

    /* (non-Javadoc)
     * @see de.desjardins.ol3.demo.client.example.Example#show()
     */
	@Override
    public void show(String exampleId) {
		logger.log(Level.INFO, "GeolocationExample.show()");

        // create a OSM-layer
        XyzOptions osmSourceOptions = OLFactory.createOptions();

        Osm osmSource = new Osm(osmSourceOptions);
        LayerOptions osmLayerOptions = OLFactory.createOptions();
        osmLayerOptions.setSource(osmSource);

        Tile osmLayer = new Tile(osmLayerOptions);

        Vector vector = new Vector();

        // create a view
        ViewOptions viewOptions = OLFactory.createOptions();
        View view = new View(viewOptions);

        Coordinate centerCoordinate = new Coordinate(-0.1275, 51.507222);
        Coordinate transformedCenterCoordinate = Projection.transform(centerCoordinate, OpenLayersConstants.EPSG_4326, OpenLayersConstants.EPSG_3857);

        view.setCenter(transformedCenterCoordinate);
        view.setZoom(10);

        // create the map
        MapOptions mapOptions = OLFactory.createOptions();
        mapOptions.setTarget(exampleId);
        mapOptions.setView(view);

        Map map = new Map(mapOptions);

        map.addLayer(osmLayer);
        map.addLayer(vector);

        // add some controls
        map.addControl(new ScaleLine());
        OpenLayersUtils.addDefaultControls(map.getControls());

        Attribution attribution = new Attribution();
        attribution.setCollapsed(true);

        map.addControl(attribution);

        // add some interactions
        map.addInteraction(new KeyboardPan());
        map.addInteraction(new KeyboardZoom());

        ol.source.Vector vectorSource = new ol.source.Vector();
        vector.setSource(vectorSource);

        Feature positionFeature = new Feature();
        Feature accuracyFeature = new Feature();

        vectorSource.addFeature(accuracyFeature);
        vectorSource.addFeature(positionFeature);

        PositionOptions positionOptions = new PositionOptions();
        positionOptions.setEnableHighAccuracy(true);

        GeolocationOptions geolocationOptions = new GeolocationOptions();
        geolocationOptions.setTrackingOptions(positionOptions);
        geolocationOptions.setProjection(view.getProjection());

        Geolocation geolocation = new Geolocation(geolocationOptions);

        geolocation.addChangeListener((Event event) -> {

            positionFeature.setGeometry(new Point(geolocation.getPosition()));
            view.setCenter(geolocation.getPosition());

        });

        ViewFitOptions viewFitOptions = OLFactory.createOptions();
        viewFitOptions.setSize(new Size(100, 100));

        geolocation.on("change:accuracyGeometry", (Event event) -> {

            accuracyFeature.setGeometry(geolocation.getAccuracyGeometry());
            view.fit(geolocation.getAccuracyGeometry(), viewFitOptions);

        });

        geolocation.on("error", (Event event) -> {

            Window.alert("Could't determine location!");

        });

        // start tracking
        geolocation.setTracking(true);

    }

}

