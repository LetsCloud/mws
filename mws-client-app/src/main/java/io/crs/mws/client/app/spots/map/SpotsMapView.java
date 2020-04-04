/**
 * 
 */
package io.crs.mws.client.app.spots.map;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import io.crs.mws.client.app.resources.AppResources;
import io.crs.mws.shared.dto.WindspotDto;
import ol.Collection;
import ol.Coordinate;
import ol.EventsKey;
import ol.Feature;
import ol.FeatureOptions;
import ol.Map;
import ol.MapOptions;
import ol.OLFactory;
import ol.Overlay;
import ol.OverlayOptions;
import ol.View;
import ol.event.EventListener;
import ol.events.condition.Condition;
import ol.geocoder.AddressChosenEvent;
import ol.geocoder.Geocoder;
import ol.geocoder.GeocoderOptions;
import ol.geom.Point;
import ol.interaction.Select;
import ol.interaction.SelectOptions;
import ol.layer.Base;
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
public class SpotsMapView extends ViewWithUiHandlers<SpotsMapUiHandlers> implements SpotsMapPresenter.MyView {
	private static Logger logger = Logger.getLogger(SpotsMapView.class.getName());

	interface Binder extends UiBinder<Widget, SpotsMapView> {
	}

	private static final String MAP_PANEL = "mapPanel";

	private final AppResources res;
	
	private SpotCreator spotCreator = new SpotCreator();

	private EventsKey unByKey;

	@UiField
	HTMLPanel mapPanel, creatorPanel;

	/**
	* 
	*/
	@Inject
	SpotsMapView(Binder uiBinder, AppResources res) {
		logger.info("SpotsMapView()");
		this.res = res;
		initWidget(uiBinder.createAndBindUi(this));

		creatorPanel.add(spotCreator);
		mapPanel.getElement().setId(MAP_PANEL);
	}

	@Override
	public void reveal(List<WindspotDto> windpots) {
		mapPanel.getElement().removeAllChildren();

		Scheduler.get().scheduleDeferred(() -> showMap(MAP_PANEL, windpots));
	}

	@Override
	public void refresh() {
	}

	private Feature createFeature(WindspotDto windspot) {
		Coordinate coordinate = OLFactory.createCoordinate(Double.parseDouble(windspot.getCoordinateX()),
				Double.parseDouble(windspot.getCoordinateY()));
		Point point = new Point(coordinate);
		FeatureOptions featureOptions = OLFactory.createOptions();
		featureOptions.setGeometry(point);
		return new Feature(featureOptions);
	}

	private ol.layer.Vector createVectorLayer(Collection<Feature> features) {
		// create source
		VectorOptions vectorSourceOptions = OLFactory.createOptions();
		vectorSourceOptions.setFeatures(features);
		Vector vectorSource = new Vector(vectorSourceOptions);

		// create style
		StyleOptions styleOptions = new StyleOptions();
		IconOptions iconOptions = new IconOptions();
//		iconOptions.setSrc("https://openlayers.org/en/v3.20.1/examples/data/icon.png");
//		iconOptions.setSrc("http://localhost:8080/image/icon-dot_32.png");
		iconOptions.setSrc(res.spotImg().getSafeUri().asString());
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

		ol.layer.Vector vectorLayer = new ol.layer.Vector(vectorLayerOptions);

		return vectorLayer;
	}

	private Tile createOsmLayer() {
		// create a OSM-layer
		XyzOptions osmSourceOptions = OLFactory.createOptions();
		Osm osmSource = new Osm(osmSourceOptions);

		LayerOptions osmLayerOptions = OLFactory.createOptions();
		osmLayerOptions.setSource(osmSource);

		Tile osmLayer = new Tile(osmLayerOptions);

		return osmLayer;
	}

	private View createView() {
		// create a view
		View view = new View();
		Coordinate centerCoordinate = OLFactory.createCoordinate(0, 0);
		view.setCenter(centerCoordinate);
		view.setZoom(2);

		return view;
	}

	private void showMap(String mapPanelId, List<WindspotDto> windpots) {

		Collection<Feature> features = new Collection<Feature>();
		for (WindspotDto windspot : windpots) {
			features.push(createFeature(windspot));
		}

		Collection<Base> lstLayer = new Collection<Base>();
		lstLayer.push(createOsmLayer());
		lstLayer.push(createVectorLayer(features));

		// create the map
		MapOptions mapOptions = new MapOptions();
		mapOptions.setTarget(mapPanelId);
		mapOptions.setView(createView());
		mapOptions.setLayers(lstLayer);
		Map map = new Map(mapOptions);

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
				logger.info("MarkerExample.save().onClick()");
				getUiHandlers().createSpot(spotCreator.getSpotName(), spotCreatorOverlay.getPosition().getX(),
						spotCreatorOverlay.getPosition().getY(), spotCreatorOverlay.getPosition().getZ());
				spotCreatorOverlay.setPosition(null);
			}
		});
		spotCreator.addCloseLinkClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				logger.info("MarkerExample.close().onClick()");
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
//		overlayOptions.setOffnset(OLFactory.createPixel(-300, 0));
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

		Select interaction = createInteraction();
		map.addInteraction(interaction);

		map.on("singleclick", new EventListener<AddressChosenEvent>() {
			@Override
			public void onEvent(AddressChosenEvent event) {
				logger.info("MarkerExample->event.getType()=" + event.getType());
				Collection<Feature> selectedFeatures = interaction.getFeatures();
				if (selectedFeatures.isEmpty()) {
					spotCreatorOverlay.setPosition(event.getCoordinate());
				}
			}
		});
	}

	private Select createInteraction() {
		SelectOptions selectOptions = new SelectOptions();
		selectOptions.setCondition(Condition.getClick());

		// create a select interaction
		final Select selectFeature = new Select(selectOptions);
		selectFeature.on("select", (Select.Event event) -> {
			logger.info("MarkerExample.createInteraction().onSelect()");
		});
		return selectFeature;
	}

	private Select createInteraction2(Overlay spotCreatorOverlay) {
		SelectOptions selectOptions = new SelectOptions();
		selectOptions.setCondition(Condition.getClick());

		// create a select interaction
		final Select selectFeature = new Select(selectOptions);
// map.addInteraction(selectFeature);

		selectFeature.on("select", (Select.Event event) -> {
			logger.info("MarkerExample.createInteraction().onSelect()");

			Feature[] selectedFeatures = event.getSelected();
//			event.preventDefault();
//			event.stopPropagation();
//			logger.info("MarkerExample.createInteraction().onSelect()-2");

			if (selectedFeatures.length > 0) {
				logger.info("MarkerExample.createInteraction().onSelect()->(selectedFeatures.length > 0)");
				Feature selectedFeature = selectedFeatures[0];
				String output = "You selected feature with id '" + selectedFeature.getId() + "'" + " and name '"
						+ selectedFeature.get("name") + "'" + " and geometry name '" + selectedFeature.getGeometryName()
						+ "'" + ".";
				logger.info("SpotsMapView().createInteraction()->output=" + output);
//				Window.alert(output);
			} else {
//				logger.info("MarkerExample.createInteraction().onSelect()->(selectedFeatures.length == 0)");
				spotCreatorOverlay.setPosition(event.getMapBrowserEvent().getCoordinate());
			}

		});
		return selectFeature;
	}
}