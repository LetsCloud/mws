/**
 * 
 */
package io.crs.mws.client.core.gin;

import javax.inject.Inject;

import com.google.gwt.dom.client.StyleInjector;

import io.crs.mws.client.core.resources.CoreResources;

/**
 * @author CR
 *
 */
public class ResourceLoader {
	@Inject
    ResourceLoader(CoreResources resources) {
		StyleInjector.injectAtStart(resources.materialize().getText());
		StyleInjector.injectAtEnd(resources.core().getText());
		
//		resources.coreCss().ensureInjected();
    }
}
