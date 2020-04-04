/**
 * 
 */
package io.crs.mws.client.core.app;

import java.util.logging.Logger;

import javax.inject.Inject;

/**
 * @author robi
 *
 */
public class AdmServiceWorkerManager extends BaseServiceWorkerManager {
	private static Logger logger = Logger.getLogger(AdmServiceWorkerManager.class.getName());
	
	@Inject
	AdmServiceWorkerManager() {
		super();
		logger.info("AdmServiceWorkerManager()");
	}
}
