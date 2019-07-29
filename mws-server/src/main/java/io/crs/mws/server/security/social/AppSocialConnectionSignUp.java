/**
 * 
 */
package io.crs.mws.server.security.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import io.crs.mws.server.model.RegistrationForm;
import io.crs.mws.server.security.local.LocalUser;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.cnst.SocialProvider;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

/**
 * @author robi
 *
 */
public class AppSocialConnectionSignUp implements ConnectionSignUp {

	private final AccountService accountService;

	public AppSocialConnectionSignUp(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public String execute(final Connection<?> connection) {
		RegistrationForm userDetails = toUserRegistrationObject(connection.getKey().getProviderUserId(),
				SecurityUtil.toSocialProvider(connection.getKey().getProviderId()), connection.fetchUserProfile());
		try {
			LocalUser user = (LocalUser) accountService.registerAccount(userDetails);
			return user.getUserId();
		} catch (EntityValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UniqueIndexConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private RegistrationForm toUserRegistrationObject(final String userId, final SocialProvider socialProvider,
			final UserProfile userProfile) {
		return RegistrationForm.getBuilder().addUsername(userId).addNickname(userProfile.getName())
				.addEmail(userProfile.getEmail()).addPassword(userProfile.getName()).addSocialProvider(socialProvider)
				.build();
	}

}
