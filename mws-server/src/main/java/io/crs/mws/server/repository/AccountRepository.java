/**
 * 
 */
package io.crs.mws.server.repository;

import io.crs.mws.server.entity.Account;

/**
 * @author robi
 *
 */
public interface AccountRepository extends CrudRepository<Account> {

	Account findByPrimaryKey(String primaryKey);

	Account findByEmail(String email);

	Account findByToken(String token);

	String getWebSafeKeyById(Long id);

}
