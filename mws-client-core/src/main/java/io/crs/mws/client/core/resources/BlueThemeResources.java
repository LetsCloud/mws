/**
 * 
 */
package io.crs.mws.client.core.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * @author CR
 *
 */
public interface BlueThemeResources extends ClientBundle {
	public static final BlueThemeResources INSTANCE = GWT.create(BlueThemeResources.class);

	interface Style extends CssResource {
		// Your classes here
	}

	@Source({ "io/crs/mws/client/core/resources/css/theme_hw_blue.gss",
			"io/crs/mws/client/core/resources/css/override.gss" })
	Style override();
}
