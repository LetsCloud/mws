/**
 * 
 */
package io.crs.mws.server.security.social;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.social.security.provider.SocialAuthenticationService;

/**
 * @author robi
 *
 */
public class AppSocialAuthenticationServiceRegistry extends SocialAuthenticationServiceRegistry {
	private static final Logger logger = LoggerFactory.getLogger(AppSocialAuthenticationServiceRegistry.class);

	private List<SocialAuthenticationService<?>> authenticationServices;

	public AppSocialAuthenticationServiceRegistry(final List<SocialAuthenticationService<?>> authenticationServices) {
		logger.info("AppSocialAuthenticationServiceRegistry()");
		this.authenticationServices = authenticationServices;
		for (SocialAuthenticationService authenticationService : authenticationServices) {
			logger.info("authenticationService.getConnectionCardinality()="
					+ authenticationService.getConnectionCardinality());
		}
	}

	@PostConstruct
	public void init() {
		if (authenticationServices != null) {
			for (SocialAuthenticationService authenticationService : authenticationServices) {
				super.addAuthenticationService(authenticationService);
			}
		}
	}

}
