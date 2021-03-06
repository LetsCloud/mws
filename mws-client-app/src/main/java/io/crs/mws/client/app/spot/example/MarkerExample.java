/**
 * 
 */
package io.crs.mws.client.app.spot.example;

import ol.Collection;
import ol.Coordinate;
import ol.Feature;
import ol.FeatureOptions;
import ol.Map;
import ol.MapBrowserEvent;
import ol.MapOptions;
import ol.OLFactory;
import ol.View;
import ol.control.Rotate;
import ol.control.ScaleLine;
import ol.event.EventListener;
import ol.geom.Point;
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
import ol.style.Icon;
import ol.style.IconOptions;
import ol.style.Style;
import ol.style.StyleOptions;

/**
 * Example about using markers.
 *
 * @author Tino Desjardins
 */
public class MarkerExample implements Example {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.desjardins.ol3.demo.client.example.Example#show()
	 */
	@Override
	public void show(String exampleId) {

		// create a point
		Coordinate coordinate1 = OLFactory.createCoordinate(4e6, 2e6);
		Point point1 = new Point(coordinate1);

		// create a point2
		Coordinate coordinate2 = OLFactory.createCoordinate(2e6, 3e6);
		Point point2 = new Point(coordinate2);

		// create feature
		FeatureOptions featureOptions = OLFactory.createOptions();
		featureOptions.setGeometry(point1);
		Feature feature = new Feature(featureOptions);

		FeatureOptions featureOptions2 = OLFactory.createOptions();
		featureOptions2.setGeometry(point2);
		Feature feature2 = new Feature(featureOptions2);

		Collection<Feature> features = new Collection<Feature>();
		features.push(feature);
		features.push(feature2);

		// create source
		VectorOptions vectorSourceOptions = OLFactory.createOptions();
		vectorSourceOptions.setFeatures(features);
		Vector vectorSource = new Vector(vectorSourceOptions);

		// create style
		StyleOptions styleOptions = new StyleOptions();
		IconOptions iconOptions = new IconOptions();
		iconOptions.setSrc("https://openlayers.org/en/v3.20.1/examples/data/icon.png");
		Icon icon = new Icon(iconOptions);
		styleOptions.setImage(icon);
		Style style = new Style(styleOptions);

		VectorLayerOptions vectorLayerOptions = OLFactory.createOptions();
		vectorLayerOptions.setSource(vectorSource);
		vectorLayerOptions.setStyle(style);

		ol.layer.Vector vectorLayer = new ol.layer.Vector(vectorLayerOptions);

		// create a OSM-layer Open Street Map
		XyzOptions osmSourceOptions = OLFactory.createOptions();
		Osm osmSource = new Osm(osmSourceOptions);

		LayerOptions osmLayerOptions = OLFactory.createOptions();
		osmLayerOptions.setSource(osmSource);

		Tile osmLayer = new Tile(osmLayerOptions);

		// create a view
		View view = new View();
		Coordinate centerCoordinate = OLFactory.createCoordinate(0, 0);
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

		map.addDoubleClickListener(new EventListener<MapBrowserEvent>() {

			@Override
			public void onEvent(MapBrowserEvent event) {
				Coordinate c = event.getCoordinate();
				// TODO Auto-generated method stub

			}
		});
		// add some controls
		map.addControl(new ScaleLine());
		DemoUtils.addDefaultControls(map.getControls());

		// add some interactions
		map.addInteraction(new KeyboardPan());
		map.addInteraction(new KeyboardZoom());
		map.addControl(new Rotate());

	}

}
