/**
 * 
 */
package io.crs.mws.client.app.spot.example;

import io.crs.mws.client.app.spot.OpenLayersUtils;
import ol.Collection;
import ol.Coordinate;
import ol.Feature;
import ol.FeatureOptions;
import ol.Map;
import ol.MapOptions;
import ol.View;
import ol.control.Rotate;
import ol.control.ScaleLine;
import ol.format.GeoJson;
import ol.geom.Circle;
import ol.geom.LineString;
import ol.interaction.KeyboardPan;
import ol.interaction.KeyboardZoom;
import ol.layer.Base;
import ol.layer.LayerOptions;
import ol.layer.Tile;
import ol.layer.VectorLayerOptions;
import ol.source.Osm;
import ol.source.Vector;
import ol.source.VectorOptions;
import ol.source.XyzOptions;

/**
 * Example of GeoJSON format.
 *
 * @author Tobias Lochmann
 */
public class GeoJsonExample implements Example {

    /* (non-Javadoc)
     * @see de.desjardins.ol3.demo.client.example.Example#show() */
    @Override
    public void show(String exampleId) {

        // create linestring
        Coordinate coordinate1 = new Coordinate(4e6, 2e6);
        Coordinate coordinate2 = new Coordinate(8e6, -2e6);
        Coordinate[] coordinates = { coordinate1, coordinate2 };

        Circle circle1 = new Circle(coordinate1, 100);
        
        LineString lineString = new LineString(coordinates);

        // create feature
        FeatureOptions featureOptions = new FeatureOptions();
        featureOptions.setGeometry(lineString);
//        featureOptions.setGeometry(circle1);
          
        Feature feature = new Feature(featureOptions);

        // convert feature to GeoJSON
        GeoJson geoJsonFormat = new GeoJson();
        java.lang.Object geoJson = geoJsonFormat.writeFeatureObject(feature, null);

        // convert features from GeoJSON
        Feature featureGeoJson = geoJsonFormat.readFeature(geoJson, null);

        // show converted features
        Collection<Feature> lstFeatures = new Collection<Feature>();
        lstFeatures.push(featureGeoJson);

        VectorOptions vectorSourceOptions = new VectorOptions();
        vectorSourceOptions.setFeatures(lstFeatures);
        Vector vectorSource = new Vector(vectorSourceOptions);

        VectorLayerOptions vectorLayerOptions = new VectorLayerOptions();
        vectorLayerOptions.setSource(vectorSource);
        ol.layer.Vector vectorLayer = new ol.layer.Vector(vectorLayerOptions);

        // create a OSM-layer
        XyzOptions osmSourceOptions = new XyzOptions();
        Osm osmSource = new Osm(osmSourceOptions);

        LayerOptions osmLayerOptions = new LayerOptions();
        osmLayerOptions.setSource(osmSource);

        Tile osmLayer = new Tile(osmLayerOptions);

        // create a view
        View view = new View();
        Coordinate centerCoordinate = new Coordinate(0, 0);
        view.setCenter(centerCoordinate);
        view.setZoom(2);

        // create the map
        MapOptions mapOptions = new MapOptions();
        mapOptions.setTarget(exampleId);
        mapOptions.setView(view);
        Collection<Base> lstLayer = new Collection<Base>();
        lstLayer.push(osmLayer);
        lstLayer.push(vectorLayer);
        mapOptions.setLayers(lstLayer);
        Map map = new Map(mapOptions);

        // add some controls
        map.addControl(new ScaleLine());
        OpenLayersUtils.addDefaultControls(map.getControls());

        // add some interactions
        map.addInteraction(new KeyboardPan());
        map.addInteraction(new KeyboardZoom());
        map.addControl(new Rotate());

    }

}