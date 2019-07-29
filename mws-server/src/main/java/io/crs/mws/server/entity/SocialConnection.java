/**
 * 
 */
package io.crs.mws.server.entity;

/**
 * A data transfer object that allows the internal state of a Connection to be
 * persisted and transferred between layers of an application. Some fields may
 * be null depending on the specific type of {@link Connection}. For example, an
 * {@link OAuth2Connection} has a null 'secret' field while an
 * {@link OAuth1Connection} has null 'refreshToken' and 'expireTime' fields.
 * 
 * @author robi
 * @see Connection#createData()
 *
 */
public class SocialConnection {

	private String providerId;

	private String providerUserId;

	private String displayName;

	private String profileUrl;

	private String imageUrl;

	private String accessToken;

	private String secret;

	private String refreshToken;

	private Long expireTime;

	public SocialConnection(String providerId, String providerUserId, String displayName, String profileUrl,
			String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime) {
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.displayName = displayName;
		this.profileUrl = profileUrl;
		this.imageUrl = imageUrl;
		this.accessToken = accessToken;
		this.secret = secret;
		this.refreshToken = refreshToken;
		this.expireTime = expireTime;
	}

	/**
	 * The id of the provider the connection is associated with.
	 * 
	 * @return The id of the provider the connection is associated with.
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * The id of the provider user this connection is connected to.
	 * 
	 * @return The id of the provider user this connection is connected to.
	 */
	public String getProviderUserId() {
		return providerUserId;
	}

	/**
	 * A display name for the connection.
	 * 
	 * @return A display name for the connection.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * A link to the provider's user profile page.
	 * 
	 * @return A link to the provider's user profile page.
	 */
	public String getProfileUrl() {
		return profileUrl;
	}

	/**
	 * An image visualizing the connection.
	 * 
	 * @return An image visualizing the connection.
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * The access token required to make authorized API calls.
	 * 
	 * @return The access token required to make authorized API calls.
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * The secret token needed to make authorized API calls. Required for
	 * OAuth1-based connections.
	 * 
	 * @return The secret token needed to make authorized API calls.
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * A token use to renew this connection. Optional. Always null for OAuth1-based
	 * connections.
	 * 
	 * @return A token use to renew this connection. Optional.
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * The time the connection expires. Optional. Always null for OAuth1-based
	 * connections.
	 * 
	 * @return The time the connection expires. Optional.
	 */
	public Long getExpireTime() {
		return expireTime;
	}

}
