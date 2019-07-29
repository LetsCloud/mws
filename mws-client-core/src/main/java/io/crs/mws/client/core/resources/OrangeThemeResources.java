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
public interface OrangeThemeResources extends ClientBundle {
	public static final OrangeThemeResources INSTANCE = GWT.create(OrangeThemeResources.class);

	interface Style extends CssResource {
		// Your classes here
	}

	@Source({ "io/crs/mws/client/core/resources/css/theme_hw_orange.gss",
			"io/crs/mws/client/core/resources/css/override.gss" })
	Style override();
}
