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

	@Source("io/crs/mws/client/core/resources/css/core.css")
	TextResource core();

}