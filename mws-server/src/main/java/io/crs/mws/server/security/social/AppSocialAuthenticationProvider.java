/**
 * 
 */
package io.crs.mws.server.security.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationProvider;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author robi
 *
 */
public class AppSocialAuthenticationProvider extends SocialAuthenticationProvider{
	private static final Logger logger = LoggerFactory.getLogger(AppSocialAuthenticationProvider.class);

	public AppSocialAuthenticationProvider(UsersConnectionRepository usersConnectionRepository,
			SocialUserDetailsService userDetailsService) {
		super(usersConnectionRepository, userDetailsService);
		logger.info("AppSocialAuthenticationProvider()");
	}

	@Override
	protected String toUserId(Connection<?> connection) {
		String result = super.toUserId(connection);
		logger.info("toUserId->result=" + result);
		return result;
	}

}
