/**
 * 
 */
package io.crs.mws.server.service.ofy;

import java.util.ArrayList;
import java.util.List;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.entity.AccountChild;
import io.crs.mws.server.repository.CrudRepository;
import io.crs.mws.server.service.AccountChildService;
import io.crs.mws.server.service.AccountService;

/**
 * @author robi
 *
 */
public abstract class AccountChildServiceImpl<T extends AccountChild, R extends CrudRepository<T>>
		extends CrudServiceImpl<T, R> implements AccountChildService<T> {

	protected final AccountService accountService;

	public AccountChildServiceImpl(R repository, AccountService accountService) {
		super(repository);
		this.accountService = accountService;
	}

	@Override
	protected List<Object> getParents(Long accountId) {
		List<Object> parents = new ArrayList<Object>();
		parents.add(accountService.findById(accountId));
		return parents;
	}

	@Override
	protected List<Object> getParents(String accountWebSafeKey) {
		List<Object> parents = new ArrayList<Object>();
		parents.add(accountService.findByWebSafeKey(accountWebSafeKey));
		return parents;
	}

	@Override
	public Account getCurrentAccount(String email) {
		Account account = accountService.findByEmail(email);
		if (account == null)
			return null;
		return account;
	}
}
