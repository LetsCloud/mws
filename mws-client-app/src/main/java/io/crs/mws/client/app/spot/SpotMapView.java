/**
 * 
 */
package io.crs.mws.client.app.spot;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import ol.Collection;
import ol.Coordinate;
import ol.Feature;
import ol.FeatureOptions;
import ol.Map;
import ol.MapOptions;
import ol.OLFactory;
import ol.Overlay;
import ol.OverlayOptions;
import ol.View;
import ol.event.EventListener;
import ol.geocoder.AddressChosenEvent;
import ol.geocoder.Geocoder;
import ol.geocoder.GeocoderOptions;
import ol.geom.Point;
import ol.layer.Base;
import ol.layer.Layer;
import ol.layer.LayerOptions;
import ol.layer.Tile;
import ol.layer.VectorLayerOptions;
import ol.popup.Popup;
import ol.source.Osm;
import ol.source.Vector;
import ol.source.VectorOptions;
import ol.source.XyzOptions;
import ol.style.Icon;
import ol.style.IconOptions;
import ol.style.Style;
import ol.style.StyleOptions;

/**
 * @author robi
 *
 */
public class SpotMapView extends ViewWithUiHandlers<SpotMapUiHandlers> implements SpotMapPresenter.MyView {
	private static Logger logger = Logger.getLogger(SpotMapView.class.getName());

	interface Binder extends UiBinder<Widget, SpotMapView> {
	}

	private Map map;
	private Tile osmLayer;
	private ol.layer.Vector vectorLayer;
	private SpotCreator spotCreator = new SpotCreator();

	@UiField
	SimplePanel panel, creatorPanel;

	@Inject
	SpotMapView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		logger.log(Level.INFO, "SpotMapView");
		creatorPanel.add(spotCreator);
	}

	@Override
	public void start() {
		panel.setSize("100%", "100%");
		panel.getElement().setId("spotMap");

		panel.setVisible(true);

		Scheduler.get().scheduleDeferred(() -> showMap("spotMap"));
		/*
		 * Timer t = new Timer() {
		 * 
		 * @Override public void run() { logger.info("SpotMapView().run->start");
		 * example.getExample().show(example.getExample().toString());
		 * logger.info("SpotMapView().run->end"); } }; t.schedule(100);
		 */
	}

	private void showMap(String mapPanelId) {

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
		/*
		 * Text t = new Text();
		 * t.setFont("bold 20px 'Open Sans', 'Arial Unicode MS', 'sans-serif'");
		 * styleOptions.setText(t);
		 */
		Style style = new Style(styleOptions);

		VectorLayerOptions vectorLayerOptions = OLFactory.createOptions();
		vectorLayerOptions.setSource(vectorSource);
		vectorLayerOptions.setStyle(style);

		vectorLayer = new ol.layer.Vector(vectorLayerOptions);

		// create a OSM-layer
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
		mapOptions.setTarget(mapPanelId);
		mapOptions.setView(view);

		Collection<Base> lstLayer = new Collection<Base>();
		lstLayer.push(osmLayer);
		lstLayer.push(vectorLayer);
		mapOptions.setLayers(lstLayer);
		map = new Map(mapOptions);

		// add some controls
//		map.addControl(new ScaleLine());
//		DemoUtils.addDefaultControls(map.getControls());

		// add some interactions
//		map.addInteraction(new KeyboardPan());
//		map.addInteraction(new KeyboardZoom());
//		map.addControl(new Rotate());

		OverlayOptions spotCreatorOverlayOptions = OLFactory.createOptions();
		spotCreatorOverlayOptions.setElement(spotCreator.getElement());
		Overlay spotCreatorOverlay = new Overlay(spotCreatorOverlayOptions);
		spotCreator.addSaveLinkClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				logger.info("MarkerExample.show().onClick()");
				getUiHandlers().createSpot(spotCreator.getSpotName(), spotCreatorOverlay.getPosition().getX(),
						spotCreatorOverlay.getPosition().getY(), spotCreatorOverlay.getPosition().getZ());
				spotCreatorOverlay.setPosition(null);
			}
		});
		spotCreator.addCloseLinkClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				logger.info("MarkerExample.show().onClick()");
				spotCreatorOverlay.setPosition(null);
			}
		});
		map.addOverlay(spotCreatorOverlay);

		/*
		 * Coordinate transformedCenterCoordinate =
		 * Projection.transform(centerCoordinate, DemoConstants.EPSG_4326,
		 * DemoConstants.EPSG_3857);
		 */

		OverlayOptions overlayOptions = OLFactory.createOptions();
//		overlayOptions.setElement(Document.get().getElementById("popup"));
//		overlayOptions.setPosition(transformedCenterCoordinate);
//		overlayOptions.setOffset(OLFactory.createPixel(-300, 0));
		Popup popup = new Popup(overlayOptions);
		map.addOverlay(popup);

		GeocoderOptions geocoderOptions = OLFactory.createOptions();
		geocoderOptions.setProvider("osm");
		geocoderOptions.setLang("hu");
		geocoderOptions.setPlaceholder("Keresés ...");
		geocoderOptions.setLimit(5);
		geocoderOptions.setDebug(false);
		geocoderOptions.setAutoComplete(true);
		geocoderOptions.setKeepOpen(true);

		Geocoder geocoder = new Geocoder("nominatim", geocoderOptions);
		geocoder.on("addresschosen", new EventListener<AddressChosenEvent>() {

			@Override
			public void onEvent(AddressChosenEvent event) {
				// TODO Auto-generated method stub

			}
		});
		map.addControl(geocoder);

		map.on("singleclick", new EventListener<AddressChosenEvent>() {
			@Override
			public void onEvent(AddressChosenEvent event) {
				spotCreatorOverlay.setPosition(event.getCoordinate());
			}
		});
	}

	@Override
	public void renderMap() {
		logger.info("SpotMapView.renderMap()-1");
	}
}
