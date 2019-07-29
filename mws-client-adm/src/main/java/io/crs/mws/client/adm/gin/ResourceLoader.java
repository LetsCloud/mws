/**
 * 
 */
package io.crs.mws.client.adm.gin;

import javax.inject.Inject;

import io.crs.mws.client.adm.resources.AdminResources;
import io.crs.mws.client.core.resources.GreyThemeResources;

/**
 * @author CR
 *
 */
public class ResourceLoader {
	@Inject
	ResourceLoader(AdminResources adminResources, GreyThemeResources resources) {
		resources.override().ensureInjected();
	}
}
