/**
 * 
 */
package io.crs.mws.server.service;

import io.crs.mws.server.entity.Account;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

/**
 * @author CR
 *
 */
public interface AccountService extends CrudService<Account> {

	Boolean existsByEmail(String email);
	
	Account createVerificationToken(String accountKey, String token) throws Throwable;

	Boolean activate(String token) throws EntityValidationException, UniqueIndexConflictException;

	void fcmSubscribe(String webSafeKey, String iidToken, String userAgent) throws Throwable;

	Account findByEmail(String email);

	Boolean resetPassword(String webSafeKey) throws EntityValidationException, UniqueIndexConflictException;
}