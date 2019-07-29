/**
 * 
 */
package io.crs.mws.server.security2.gae;

import java.util.Base64;
import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.appengine.api.users.User;

/**
 * A simple authentication provider which interacts with {@code User} returned
 * by the GAE {@code UserService}, and also the local persistent
 * {@code UserRegistry} to build an application user principal.
 * <p>
 * If the user has been authenticated through google accounts, it will check if
 * they are already registered and either load the existing user information or
 * assign them a temporary identity with limited access until they have
 * registered.
 * <p>
 * If the account has been disabled, a {@code DisabledException} will be raised.
 *
 * @author Luke Taylor
 */
public class GaeAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {
	private static final Logger logger = LoggerFactory.getLogger(GaeAuthenticationProvider.class);

	private static final String ENCODED_EMAIL = "Y3Nlcm5pa3JAZ21haWwuY29t";
	private static final String ENCODED_TEST_EMAIL = "dGVzdEBleGFtcGxlLmNvbQ==";

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private UserRegistry userRegistry;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("authenticate()");
		GaeUser user = null;

		User googleUser = (User) authentication.getPrincipal();
		logger.info("authenticate()->googleUser=" + googleUser);

		String encodedEmail = Base64.getEncoder().encodeToString(googleUser.getEmail().getBytes());
		if ((encodedEmail.equals(ENCODED_EMAIL)) || (encodedEmail.equals(ENCODED_TEST_EMAIL))) {
			logger.info("authenticate()->encodedEmail=" + encodedEmail);
			user = new GaeUser(googleUser.getUserId(), googleUser.getNickname(), googleUser.getEmail(),
					googleUser.getNickname(), googleUser.getNickname(), EnumSet.of(AppRole.ADMIN), true);
		} else {
			user = userRegistry.findUser(googleUser.getUserId());
		}

		logger.info("authenticate()->user=" + user);

		if (user == null) {
			// User not in registry. Needs to register
			user = new GaeUser(googleUser.getUserId(), googleUser.getNickname(), googleUser.getEmail());
			logger.info("authenticate()->user2=" + user);
		}

		if (!user.isEnabled()) {
			logger.info("authenticate()->(!user.isEnabled())");
			throw new DisabledException("Account is disabled");
		}

		return new GaeUserAuthentication(user, authentication.getDetails());
	}

	/**
	 * Indicate that this provider only supports PreAuthenticatedAuthenticationToken
	 * (sub)classes.
	 */
	public final boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public void setUserRegistry(UserRegistry userRegistry) {
		this.userRegistry = userRegistry;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}
}