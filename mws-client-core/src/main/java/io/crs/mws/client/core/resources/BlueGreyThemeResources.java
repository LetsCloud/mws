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
public interface BlueGreyThemeResources extends ClientBundle {
	public static final BlueGreyThemeResources INSTANCE = GWT.create(BlueGreyThemeResources.class);

	interface Style extends CssResource {
		// Your classes here
	}

	@Source({ "io/crs/mws/client/core/resources/css/theme_blue-grey.gss",
			"io/crs/mws/client/core/resources/css/override.gss" })
	Style override();
}
