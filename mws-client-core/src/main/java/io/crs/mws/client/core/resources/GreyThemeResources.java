/**
 * 
 */
package io.crs.mws.client.core.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * @author robi
 *
 */
public interface GreyThemeResources extends ClientBundle {
	public static final GreyThemeResources INSTANCE = GWT.create(GreyThemeResources.class);

	interface Style extends CssResource {
		// Your classes here
	}

	@Source({ "io/crs/mws/client/core/resources/css/grey_theme.gss",
			"io/crs/mws/client/core/resources/css/override.gss" })
	Style override();
}
