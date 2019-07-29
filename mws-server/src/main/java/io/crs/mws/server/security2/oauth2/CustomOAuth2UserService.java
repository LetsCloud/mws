/**
 * 
 */
package io.crs.mws.server.security2.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.security2.UserPrincipal;
import io.crs.mws.server.security2.oauth2.exception.OAuth2AuthenticationProcessingException;
import io.crs.mws.server.security2.oauth2.user.OAuth2UserInfo;
import io.crs.mws.server.security2.oauth2.user.OAuth2UserInfoFactory;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.cnst.SocialProvider;

/**
 * @author robi
 *
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

	@Autowired
	private AccountService userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		logger.info("loadUser()");
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest.getClientRegistration(), oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	/**
	 * Builds the security principal from the given userReqest. Registers the user
	 * if not already reqistered
	 */
	public UserPrincipal processOAuth2User(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
		logger.info("processOAuth2User()");
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(clientRegistration.getRegistrationId(),
				oAuth2User.getAttributes());
		
		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		Account user = userRepository.findByEmail(oAuth2UserInfo.getEmail());

		if (user == null) {
			logger.info("processOAuth2User()->clientRegistration.getRegistrationId()="
					+ clientRegistration.getRegistrationId()+"=");
			user = registerNewUser(SocialProvider.valueOf(clientRegistration.getRegistrationId()), oAuth2UserInfo);
		} else {
/*			
			if (!user.getSocialProvider().equals(SocialProvider.valueOf(clientRegistration.getRegistrationId()))) {
				throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with "
						+ user.getSocialProvider().name() + " account. Please use your "
						+ user.getSocialProvider().name() + " account to login.");
			}
			*/
			user = updateExistingUser(user, oAuth2UserInfo);
		}

		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

	private Account registerNewUser(SocialProvider socialProvider, OAuth2UserInfo oAuth2UserInfo) {
		logger.info("registerNewUser()");
		Account user = new Account();

		user.setNickname(oAuth2UserInfo.getName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setImageUrl(oAuth2UserInfo.getImageUrl());
		try {
			return userRepository.create(user);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Account updateExistingUser(Account existingUser, OAuth2UserInfo oAuth2UserInfo) {
		logger.info("updateExistingUser()");
		existingUser.setNickname(oAuth2UserInfo.getName());
		existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
		try {
			return userRepository.update(existingUser);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}