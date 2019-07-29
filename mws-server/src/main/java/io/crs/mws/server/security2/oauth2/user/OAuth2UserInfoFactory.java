/**
 * 
 */
package io.crs.mws.server.security2.oauth2.user;

import java.util.Map;

import io.crs.mws.server.security2.oauth2.exception.OAuth2AuthenticationProcessingException;
import io.crs.mws.shared.cnst.SocialProvider;

/**
 * @author robi
 *
 */
public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(SocialProvider.google.name())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.facebook.name())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.github.name())) {
			return new GithubOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException(
					"Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}
