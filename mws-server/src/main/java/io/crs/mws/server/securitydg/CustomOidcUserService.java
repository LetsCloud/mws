/**
 * 
 */
package io.crs.mws.server.securitydg;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.repository.AccountRepository;
import io.crs.mws.server.security2.oauth2.user.GoogleOAuth2UserInfo;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

/**
 * @author robi
 *
 */
public class CustomOidcUserService extends OidcUserService {
	private static final Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

	private final AccountRepository accountRepository;

    @Autowired
	CustomOidcUserService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public OidcUser loadUser(OidcUserRequest oidUserRequest) throws OAuth2AuthenticationException {
		logger.info("loadUser()");

		OidcUser oidcUser = super.loadUser(oidUserRequest);
		
		Map<String, Object> attributes = oidcUser.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo(attributes);
/*        
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setId((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("name"));
 */       
        updateUser(userInfo);
        return oidcUser;

/*
		UserPrincipal principal = customOAuth2UserService.processOAuth2User(oidUserRequest.getClientRegistration(),
				oidcUser);

		principal.setClaims(oidcUser.getClaims());
		principal.setIdToken(oidcUser.getIdToken());
		principal.setUserInfo(oidcUser.getUserInfo());
		
		return principal;
*/		
	}
	
    private void updateUser(GoogleOAuth2UserInfo userInfo) {
        Account account = accountRepository.findByEmail(userInfo.getEmail());
        if(account == null) {
            account = new Account();
        }
        account.setEmail(userInfo.getEmail());
        account.setImageUrl(userInfo.getImageUrl());
        account.setNickname(userInfo.getName());
        try {
			accountRepository.save(account);
		} catch (EntityValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UniqueIndexConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
