/**
 * 
 */
package io.crs.mws.client.app.gin;

import javax.inject.Inject;

import io.crs.mws.client.app.resources.AppResources;
import io.crs.mws.client.core.resources.BlueGreyThemeResources;

/**
 * @author CR
 *
 */
public class AppResourceLoader {
	@Inject
    AppResourceLoader(AppResources cfgResources, BlueGreyThemeResources resources) {
        resources.override().ensureInjected();
    }
}
