/**
 * 
 */
package io.crs.mws.server.repository.ofy;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.repository.AccountRepository;

/**
 * @author robi
 *
 */
@Repository
public class AccountRepositoryImpl extends CrudRepositoryImpl<Account> implements AccountRepository {

	private static final String PROPERTY_PRIMARYKEY = "primaryKey";
	private static final String PROPERTY_EMAIL = "email";

	public AccountRepositoryImpl() {
		super(Account.class);
	}

	@Override
	protected Object getParent(Account entity) {
		return null;
	}

	@Override
	public String getWebSafeKeyById(Long id) {
		Key<Account> key = Key.create(Account.class, id);
		return key.getString();
	}

	@Override
	protected Object getParentKey(String parentWebSafeKey) {
		return null;
	}

	@Override
	protected void prepareForeignKeys(String webSafeKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public Account findByPrimaryKey(String primaryKey) {
		return getFirstByProperty(PROPERTY_PRIMARYKEY, primaryKey);
	}

	@Override
	public Account findByToken(String token) {
		return getFirstByProperty("verificationTokens.token", token);
	}

	@Override
	protected void loadUniqueIndexMap(Account entiy) {
/*		
		if ((entiy.getEmail() != null) && (!entiy.getEmail().isEmpty()))
			entiy.addUniqueIndex(PROPERTY_EMAIL, entiy.getEmail(), ErrorMessageCode.APPUSER_EMAIL_ALREADY_EXISTS);

		if ((entiy.getLoginname() != null) && (!entiy.getLoginname().isEmpty()))
			entiy.addUniqueIndex(PROPERTY_NAME, entiy.getLoginname(), ErrorMessageCode.APPUSER_CODE_ALREADY_EXISTS);
*/			
	}

	@Override
	public Account findByEmail(String email) {
		return getFirstByProperty(PROPERTY_EMAIL, email);
	}

}
