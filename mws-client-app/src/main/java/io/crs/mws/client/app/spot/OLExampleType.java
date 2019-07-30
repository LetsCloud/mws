/**
 * 
 */
package io.crs.mws.client.app.spot;

/**
 * @author robi
 *
 */
public enum OLExampleType {

	/*
    AnimationExample(new AnimationExample()),
    ArcGISExample(new ArcGISExample()),
    BingMapsExample(new BingMapsExample()),
    CanvasGradientExample(new CanvasGradientExample()),
    ClusterExample(new ClusterExample()),
    ConstrainedExample(new ConstrainedViewExample()),
    GeoJSONExample(new GeoJsonExample()),
    */
    GeolocationExample(new GeolocationExample())
    /* ,
    GpxExample(new GpxExample()),
    GraticuleExample(new GraticuleExample()),
    ImageExample(new StaticImageExample()),
    MapBoxExample(new MapboxExample()),
    MapEventsExample(new MapEventsExample()),
    MapGuideExample(new MapGuideExample()),
    MarkerExample(new MarkerExample()),
    MeasureExample(new MeasureExample()),
    MvtExample(new MvtExample()),
    OsmExample(new OsmExample()),
    OverlayExample(new OverlayExample()),
    RasterExample(new RasterExample()),
    SelectFeaturesExample(new SelectFeaturesExample()),
    TileExample(new TileExample()),
    TileWmsExample(new TileWmsExample()),
    WfsExample(new WfsExample()),
    WmsExample(new WmsExample()),
    WmtsExample(new WmtsExample()),
    XyzExample(new XyzExample())
    */;

    private transient Example example;

    OLExampleType(Example example) {
        this.example = example;
    }

    public Example getExample() {
        return this.example;
    }

}