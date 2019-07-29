/**
 * 
 */
package io.crs.mws.server.google;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * @author robi
 *
 */
public class AppUsersConnectionRepository implements UsersConnectionRepository {

	private final ConnectionFactoryLocator connectionFactoryLocator;
	private final UserConectionService userConectionService;
	private final TextEncryptor textEncryptor;
	private ConnectionSignUp connectionSignUp;

	public AppUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator,
			UserConectionService userConectionService, TextEncryptor textEncryptor) {
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.userConectionService = userConectionService;
		this.textEncryptor = textEncryptor;
	}

	/**
	 * Find the ids for local application users that have the given
	 * {@link Connection}. Used to support the ProviderSignIn scenario where the
	 * user id returned is used to sign a local application user in using his or her
	 * provider account. No entries indicates no application users are associated
	 * with the connection; ProviderSignInController will offer the user a signup
	 * page to register with the app. A single entry indicates that exactly one
	 * application user is associated with the connection and is used to sign in
	 * that user via ProviderSignInController. Multiple entries indicate that
	 * multiple application users are associated with the connection and handled as
	 * an error by ProviderSignInController.
	 * 
	 * @param connection the service provider connection resulting from the provider
	 *                   sign-in attempt
	 * @return the user ids associated with the connection.
	 */
	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		ConnectionKey key = connection.getKey();
		List<String> localUserIds = userConectionService.getLocalIdsByConnection(key.getProviderId(),
				key.getProviderUserId());
		if (localUserIds.size() == 0 && connectionSignUp != null) {
			String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null) {
				createConnectionRepository(newUserId).addConnection(connection);
				return Arrays.asList(newUserId);
			}
		}
		return localUserIds;
	}

	/**
	 * Find the ids of the users who are connected to the specific provider user
	 * accounts.
	 * 
	 * @param providerId      the provider id, e.g. "facebook"
	 * @param providerUserIds the set of provider user ids e.g. ("125600", "131345",
	 *                        "54321").
	 * @return the set of user ids connected to those service provider users, or
	 *         empty if none.
	 */
	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		return userConectionService.getLocalIdsByProviderUsrIds(providerId, providerUserIds);
	}

	/**
	 * Create a single-user {@link ConnectionRepository} instance for the user
	 * assigned the given id. All operations on the returned repository instance are
	 * relative to the user.
	 * 
	 * @param userId the id of the local user account.
	 * @return the ConnectionRepository, exposing a number of operations for
	 *         accessing and updating the given user's provider connections.
	 */
	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
		return new AppConnectionRepository(userId, connectionFactoryLocator, userConectionService, textEncryptor);
	}

	/**
	 * The command to execute to create a new local user profile in the event no
	 * user id could be mapped to a connection. Allows for implicitly creating a
	 * user profile from connection data during a provider sign-in attempt. Defaults
	 * to null, indicating explicit sign-up will be required to complete the
	 * provider sign-in attempt.
	 * 
	 * @see #findUserIdsWithConnection(Connection)
	 */
	public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
		this.connectionSignUp = connectionSignUp;
	}

}
