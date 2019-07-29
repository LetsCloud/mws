/**
 * 
 */
package io.crs.mws.server.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import io.crs.mws.server.entity.SocialConnection;

/**
 * @author robi
 *
 */
public class AppConnectionRepository implements ConnectionRepository {

	private final String userId;
	private final ConnectionFactoryLocator connectionFactoryLocator;
	private final UserConectionService userConectionService;
	private final TextEncryptor textEncryptor;

	public AppConnectionRepository(String userId, ConnectionFactoryLocator connectionFactoryLocator,
			UserConectionService userConectionService, TextEncryptor textEncryptor) {
		this.userId = userId;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.userConectionService = userConectionService;
		this.textEncryptor = textEncryptor;
	}

	/**
	 * Find all connections the current user has across all providers. The returned
	 * map contains an entry for each provider the user is connected to. The key for
	 * each entry is the providerId, and the value is the list of
	 * {@link Connection}s that exist between the user and that provider. For
	 * example, if the user is connected once to Facebook and twice to Twitter, the
	 * returned map would contain two entries with the following structure:
	 * 
	 * <pre>
	 * { 
	 *     "facebook" -&gt; Connection("Keith Donald") ,
	 *     "twitter"  -&gt; Connection("kdonald"), Connection("springsource")
	 * }
	 * </pre>
	 * 
	 * The returned map is sorted by providerId and entry values are ordered by
	 * rank. Returns an empty map if the user has no connections.
	 */
	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		List<Connection<?>> resultList = new ArrayList<Connection<?>>();
		for (SocialConnection scd : userConectionService.getConnectionDataByLocalId(userId)) {
			resultList.add(connectionMapper.mapData(scd));
		}

		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) {
			connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
		}
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			connections.add(providerId, connection);
		}
		return connections;
	}

	/**
	 * Find the connections the current user has to the provider registered by the
	 * given id e.g. 'facebook'. The returned list is ordered by connection rank.
	 * Returns an empty list if the user has no connections to the provider.
	 * 
	 * @param providerId the provider id e.g. "facebook"
	 * @return the connections the user has to the provider, or an empty list if
	 *         none
	 */
	@Override
	public List<Connection<?>> findConnections(String providerId) {
		List<Connection<?>> resultList = new ArrayList<Connection<?>>();
		for (SocialConnection scd : userConectionService.getConnectionDataByLocalIdAndProviderId(userId, providerId)) {
			resultList.add(connectionMapper.mapData(scd));
		}
		return resultList;
	}

	/**
	 * Find the connections the current user has to the provider of the given API
	 * e.g. Facebook.class. Semantically equivalent to
	 * {@link #findConnections(String)}, but uses the apiType as the provider key
	 * instead of the providerId. Useful for direct use by application code to
	 * obtain parameterized Connection instances e.g.
	 * <code>List&lt;Connection&lt;Facebook&gt;&gt;</code>.
	 * 
	 * @param <A>     the API parameterized type
	 * @param apiType the API type e.g. Facebook.class or Twitter.class
	 * @return the connections the user has to the provider of the API, or an empty
	 *         list if none
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		List<?> connections = findConnections(getProviderId(apiType));
		return (List<Connection<A>>) connections;
	}

	/**
	 * Find the connections the current user has to the given provider users. The
	 * providerUsers parameter accepts a map containing an entry for each provider
	 * the caller is interested in. The key for each entry is the providerId e.g.
	 * "facebook", and the value is a list of provider user ids to fetch connections
	 * to e.g. ("126500", "34521", "127243"). The returned map has the same
	 * structure and order, except the provider userId values have been replaced by
	 * Connection instances. If no connection exists between the current user and a
	 * given provider user, a null value is returned for that position.
	 * 
	 * @param providerUserIds the provider users map
	 * @return the provider user connection map
	 */
	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
		if (providerUsers == null || providerUsers.isEmpty()) {
			throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
		}

		List<Connection<?>> resultList = new ArrayList<Connection<?>>();
		for (SocialConnection scd : userConectionService.getConnectionDataByLocalId(userId)) {
			if (providerUsers.containsKey(scd.getProviderId())) {
				if (providerUsers.get(scd.getProviderId()).contains(scd.getProviderUserId())) {
					resultList.add(connectionMapper.mapData(scd));
				}
			}
		}

		MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			List<String> userIds = providerUsers.get(providerId);
			List<Connection<?>> connections = connectionsForUsers.get(providerId);
			if (connections == null) {
				connections = new ArrayList<Connection<?>>(userIds.size());
				for (int i = 0; i < userIds.size(); i++) {
					connections.add(null);
				}
				connectionsForUsers.put(providerId, connections);
			}
			String providerUserId = connection.getKey().getProviderUserId();
			int connectionIndex = userIds.indexOf(providerUserId);
			connections.set(connectionIndex, connection);
		}
		return connectionsForUsers;
	}

	/**
	 * Get a connection for the current user by its key, which consists of the
	 * providerId + providerUserId.
	 * 
	 * @param connectionKey the service provider connection key
	 * @return the connection
	 * @throws NoSuchConnectionException if no such connection exists for the
	 *                                   current user
	 */
	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		List<SocialConnection> socialConnections = userConectionService.getConnectionDataByLocalId(userId);
		SocialConnection sc = socialConnections.stream()
				.filter(o -> o.getProviderId().equals(connectionKey.getProviderId())
						&& o.getProviderUserId().equals(connectionKey.getProviderUserId()))
				.findFirst().get();
		if (sc == null)
			return null;
		return connectionMapper.mapData(sc);
	}

	/**
	 * Get a connection between the current user and the given provider user.
	 * Semantically equivalent to {@link #getConnection(ConnectionKey)}, but uses
	 * the apiType as the provider key instead of the providerId. Useful for direct
	 * use by application code to obtain a parameterized Connection instance.
	 * 
	 * @param <A>            the API parameterized type
	 * @param apiType        the API type e.g. Facebook.class or Twitter.class
	 * @param providerUserId the provider user e.g. "126500".
	 * @return the connection
	 * @throws NoSuchConnectionException if no such connection exists for the
	 *                                   current user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	/**
	 * Get the "primary" connection the current user has to the provider of the
	 * given API e.g. Facebook.class. If the user has multiple connections to the
	 * provider associated with the given apiType, this method returns the one with
	 * the top rank (or priority). Useful for direct use by application code to
	 * obtain a parameterized Connection instance.
	 * 
	 * @param <A>     the API parameterized type
	 * @param apiType the API type e.g. Facebook.class or Twitter.class
	 * @return the primary connection
	 * @throws NotConnectedException if the user is not connected to the provider of
	 *                               the API
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
		if (connection == null) {
			throw new NotConnectedException(providerId);
		}
		return connection;
	}

	/**
	 * Find the "primary" connection the current user has to the provider of the
	 * given API e.g. Facebook.class. Semantically equivalent to
	 * {@link #getPrimaryConnection(Class)} but returns null if no connection is
	 * found instead of throwing an exception.
	 * 
	 * @param <A>     the API parameterized type
	 * @param apiType the API type e.g. Facebook.class or Twitter.class
	 * @return the primary connection, or <code>null</code> if not found
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) findPrimaryConnection(providerId);
	}

	/**
	 * Add a new connection to this repository for the current user. After the
	 * connection is added, it can be retrieved later using one of the finders
	 * defined in this interface.
	 * 
	 * @param connection the new connection to add to this repository
	 * @throws DuplicateConnectionException if the user already has this connection
	 */
	@Override
	public void addConnection(Connection<?> connection) {
		ConnectionData data = connection.createData();
		SocialConnection sc = new SocialConnection(data.getProviderId(), data.getProviderUserId(),
				data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(), encrypt(data.getAccessToken()),
				encrypt(data.getSecret()), encrypt(data.getRefreshToken()), data.getExpireTime());
		userConectionService.addConnection(userId, sc);
	}

	/**
	 * Update a Connection already added to this repository. Merges the field values
	 * of the given connection object with the values stored in the repository.
	 * 
	 * @param connection the existing connection to update in this repository
	 */
	@Override
	public void updateConnection(Connection<?> connection) {
		ConnectionData data = connection.createData();
		SocialConnection sc = new SocialConnection(data.getProviderId(), data.getProviderUserId(),
				data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(), encrypt(data.getAccessToken()),
				encrypt(data.getSecret()), encrypt(data.getRefreshToken()), data.getExpireTime());
		userConectionService.updateConnection(userId, sc);
	}

	/**
	 * Remove all Connections between the current user and the provider from this
	 * repository. Does nothing if no provider connections exist.
	 * 
	 * @param providerId the provider id e.g. 'facebook'
	 */
	@Override
	public void removeConnections(String providerId) {
		userConectionService.removeConnections(userId, providerId);
	}

	/**
	 * Remove a single Connection for the current user from this repository. Does
	 * nothing if no such connection exists.
	 * 
	 * @param connectionKey the connection key
	 */
	@Override
	public void removeConnection(ConnectionKey connectionKey) {
		userConectionService.removeConnection(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
	}

	private Connection<?> findPrimaryConnection(String providerId) {
		List<SocialConnection> socialConnections = userConectionService.getConnectionDataByLocalId(userId);
		SocialConnection sc = socialConnections.stream().filter(o -> o.getProviderId().equals(providerId)).findFirst()
				.get();
		if (sc == null)
			return null;
		return connectionMapper.mapData(sc);
	}

	private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();

	private final class ServiceProviderConnectionMapper {

		public Connection<?> mapData(SocialConnection sc) {
			ConnectionData connectionData = mapConnectionData(sc);
			ConnectionFactory<?> connectionFactory = connectionFactoryLocator
					.getConnectionFactory(connectionData.getProviderId());
			return connectionFactory.createConnection(connectionData);
		}

		private ConnectionData mapConnectionData(SocialConnection sc) {
			return new ConnectionData(sc.getProviderId(), sc.getProviderUserId(), sc.getDisplayName(),
					sc.getProfileUrl(), sc.getImageUrl(), sc.getAccessToken(), decrypt(sc.getSecret()),
					decrypt(sc.getRefreshToken()), expireTime(sc.getExpireTime()));
		}

		private String decrypt(String encryptedText) {
			return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
		}

		private Long expireTime(long expireTime) {
			return expireTime == 0 ? null : expireTime;
		}
	}

	private <A> String getProviderId(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
	}

	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}
}