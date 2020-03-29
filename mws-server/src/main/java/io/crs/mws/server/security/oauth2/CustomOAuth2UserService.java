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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.security.UserPrincipal;
import io.crs.mws.server.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import io.crs.mws.server.security.oauth2.user.OAuth2UserInfo;
import io.crs.mws.server.security.oauth2.user.OAuth2UserInfoFactory;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.cnst.Role;

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
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
	private static final String ENCODED_EMAIL = "Y3Nlcm5pa3JAZ21haWwuY29t";

	@Autowired
	private AccountService accountService;

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

		// Létrehozunk egy előfizetás objektumot
		Account account = new Account();
		// Előkészítjük a sima felhasználói jogkört
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER));

		String encodedEmail = Base64.getEncoder().encodeToString(oAuth2UserInfo.getEmail().getBytes());
		if (encodedEmail.equals(ENCODED_EMAIL)) {
			account.setEmail(oAuth2UserInfo.getEmail());
			account.setPassword(oAuth2UserInfo.getId());
			account.setNickname(oAuth2UserInfo.getName());
			account.setImageUrl(oAuth2UserInfo.getImageUrl());
			account.setEnabled(true);
			account.setRole(Role.ROLE_ADMIN);
			authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_ADMIN));
		} else {
			// Email cím alalpján rákeressünk az előfizetésre
			account = accountService.findByEmail(oAuth2UserInfo.getEmail());
			if (account == null) {
				// Ha nincs ilyen előfizetés, akkor létrehozzuk
				account = createOrUpdateAccount(new Account(), oAuth2UserInfo);
			} else {
				// Ha van, akor aktualizáljuk az adatait
				account = createOrUpdateAccount(account, oAuth2UserInfo);
			}
			account.setRole(Role.ROLE_USER);
		}
		return UserPrincipal.create(account, oAuth2User.getAttributes(), authorities);
	}

	/**
	 * 
	 * @param account
	 * @param oAuth2UserInfo
	 * @return
	 */
	private Account createOrUpdateAccount(Account account, OAuth2UserInfo oAuth2UserInfo) {
		if (account.getEmail() == null)
			account.setEmail(oAuth2UserInfo.getEmail());

		account.setNickname(oAuth2UserInfo.getName());
		account.setImageUrl(oAuth2UserInfo.getImageUrl());

		try {
			return accountService.update(account);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

}