package io.crs.mws.client.core.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

public interface CoreResources extends ClientBundle {
	public static final CoreResources INSTANCE = GWT.create(CoreResources.class);

	interface Style extends CssResource {
		String dataGroupBox();
		String dataGroupTitle();
	}

	@Source("io/crs/mws/client/core/resources/css/materialize.min.css")
	TextResource materialize();

//	@Source("io/crs/hsys/client/core/resources/css/gwt-material.css")
//	TextResource gwtMaterial();

//	@Source("img/profile.jpg")
//	ImageResource profileImg();

//	@Source("img/orange-wallpaper.jpg")
//	ImageResource orangeWallpaperImg();

	// Az appCss-ben lévő mainBackground CSS osztály használja fel
//	@Source("img/orange-wallpaper.jpg")
//	DataResource orangeWallpaperRes();

//	@Source("img/red-wallpaper.png")
//	ImageResource redWallpaperImg();

//	@Source("img/red-wallpaper.png")
//	DataResource redWallpaperRes();

	@Source("io/crs/mws/client/core/resources/css/core.css")
	TextResource core();

	@Source("io/crs/mws/client/core/resources/css/core2.css")
	TextResource coreCss();

//	@Source("js/select2-tab-fix.min.js")
	// TextResource select2TabFix();
}