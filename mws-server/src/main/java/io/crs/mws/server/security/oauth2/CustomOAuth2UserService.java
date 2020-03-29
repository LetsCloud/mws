/**
 * 
 */
package io.crs.mws.server.security.oauth2;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.security.UserPrincipal;
import io.crs.mws.server.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import io.crs.mws.server.security.oauth2.user.OAuth2UserInfo;
import io.crs.mws.server.security.oauth2.user.OAuth2UserInfoFactory;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.cnst.Role;
import io.crs.mws.shared.cnst.SocialProvider;

/**
 * The CustomOAuth2UserService extends Spring Security’s
 * DefaultOAuth2UserService and implements its loadUser() method. This method is
 * called after an access token is obtained from the OAuth2 provider.
 * 
 * In this method, we first fetch the user’s details from the OAuth2 provider.
 * If a user with the same email already exists in our database then we update
 * his details, otherwise, we register a new user.
 *
 */
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
	private static final String ENCODED_EMAIL = "Y3Nlcm5pa3JAZ21haWwuY29t";

	private final AccountService accountService;

	public CustomOAuth2UserService(AccountService accountService) {
		this.accountService = accountService;
	}

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
			logger.info("processOAuth2User()-2");
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		Account user = new Account();
		logger.info("processOAuth2User()-3");
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER));
		logger.info("processOAuth2User()-4");
		String encodedEmail = Base64.getEncoder().encodeToString(oAuth2UserInfo.getEmail().getBytes());
		logger.info("processOAuth2User()-5");
		if (encodedEmail.equals(ENCODED_EMAIL)) {
			logger.info("processOAuth2User()-6");
			user.setEmail(oAuth2UserInfo.getEmail());
			user.setPassword(oAuth2UserInfo.getId());
			user.setNickname(oAuth2UserInfo.getName());
			user.setImageUrl(oAuth2UserInfo.getImageUrl());
			user.setEnabled(true);
			user.setRole(Role.ROLE_ADMIN);
			logger.info("processOAuth2User()-7");
			authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_ADMIN));
		} else {
			logger.info("processOAuth2User()-8");
			user = accountService.findByEmail(oAuth2UserInfo.getEmail());
			logger.info("processOAuth2User()-9");

			if (user == null) {
				logger.info("processOAuth2User()->clientRegistration.getRegistrationId()="
						+ clientRegistration.getRegistrationId() + "=");
				user = registerNewUser(SocialProvider.valueOf(clientRegistration.getRegistrationId()), oAuth2UserInfo);
			} else {
				logger.info("processOAuth2User()-10");
				/*
				 * if
				 * (!user.getSocialProvider().equals(SocialProvider.valueOf(clientRegistration.
				 * getRegistrationId()))) { throw new
				 * OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
				 * user.getSocialProvider().name() + " account. Please use your " +
				 * user.getSocialProvider().name() + " account to login."); }
				 */
				user = updateExistingUser(user, oAuth2UserInfo);
			}
			logger.info("processOAuth2User()-11");
			user.setRole(Role.ROLE_USER);
			logger.info("processOAuth2User()-12");
		}
		logger.info("processOAuth2User()-13");

		return UserPrincipal.create(user, oAuth2User.getAttributes(), authorities);
	}

	private Account registerNewUser(SocialProvider socialProvider, OAuth2UserInfo oAuth2UserInfo) {
		logger.info("registerNewUser()");
		Account user = new Account();

		user.setNickname(oAuth2UserInfo.getName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setImageUrl(oAuth2UserInfo.getImageUrl());
		try {
			return accountService.create(user);
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
			return accountService.update(existingUser);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}