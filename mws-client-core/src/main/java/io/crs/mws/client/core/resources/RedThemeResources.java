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
public interface RedThemeResources extends ClientBundle {
	public static final RedThemeResources INSTANCE = GWT.create(RedThemeResources.class);

	interface Style extends CssResource {
		// Your classes here
	}

	@Source({ "io/crs/mws/client/core/resources/css/theme_hw_red.gss",
			"io/crs/mws/client/core/resources/css/override.gss" })
	Style override();
}
