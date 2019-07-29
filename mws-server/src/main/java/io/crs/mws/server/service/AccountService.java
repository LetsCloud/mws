/**
 * 
 */
package io.crs.mws.server.service;

import org.springframework.security.core.userdetails.UserDetails;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.model.RegistrationForm;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

/**
 * @author CR
 *
 */
public interface AccountService extends CrudService<Account> {

	UserDetails registerAccount(RegistrationForm registrationForm)
			throws EntityValidationException, UniqueIndexConflictException;

	Boolean existsByEmail(String email);
	
	Account createVerificationToken(String accountKey, String token) throws Throwable;

	Boolean activate(String token) throws EntityValidationException, UniqueIndexConflictException;

	void fcmSubscribe(String webSafeKey, String iidToken, String userAgent) throws Throwable;

	Account getCurrentAccount();

	Account findByEmail(String email);

	Boolean isCurrentUserLoggedIn();

	Boolean resetPassword(String webSafeKey) throws EntityValidationException, UniqueIndexConflictException;
}