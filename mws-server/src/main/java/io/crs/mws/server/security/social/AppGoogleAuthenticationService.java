/**
 * 
 */
package io.crs.mws.server.security.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.google.security.GoogleAuthenticationService;

/**
 * @author robi
 *
 */
public class AppGoogleAuthenticationService extends GoogleAuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AppGoogleAuthenticationService.class);

	public AppGoogleAuthenticationService(String apiKey, String appSecret) {
		super(apiKey, appSecret);
		logger.info("AppGoogleAuthenticationService()->apiKey=" + apiKey + ", appSecret=" + appSecret);
	}

}
