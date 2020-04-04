/**
 * 
 */
package io.crs.mws.server.service.ofy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.entity.FcmToken;
import io.crs.mws.server.entity.VerificationToken;
import io.crs.mws.server.repository.AccountRepository;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.EntityVersionConflictException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

/**
 * @author CR
 *
 */
@Service
public class AccountServiceImpl extends CrudServiceImpl<Account, AccountRepository> implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final AccountRepository repository;

	@Autowired
	public AccountServiceImpl(AccountRepository repository) {
		super(repository);
		logger.info("AccountServiceImpl(");
		this.repository = repository;
	}

	@Override
	protected List<Object> getParents(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Object> getParents(String accountWebSafeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account createVerificationToken(String accountKey, String token) throws Throwable {
//		Account account = repository.findByPrimaryKey(accountKey);
		Account account = repository.findByWebSafeKey(accountKey);
		VerificationToken myToken = new VerificationToken(token);
		List<VerificationToken> tokens = new ArrayList<VerificationToken>();
		tokens.add(myToken);
		account.setVerificationTokens(tokens);
		return repository.save(account);
	}

	@Override
	public Boolean activate(String token) throws EntityValidationException, UniqueIndexConflictException {
		Account account = repository.findByToken(token);
		account.setEnabled(true);
		repository.save(account);
		return true;
	}

	@Override
	public void fcmSubscribe(String webSafeKey, String iidToken, String userAgent) throws Throwable {
		Account currentAccount = this.findByWebSafeKey(webSafeKey);
		List<FcmToken> tokens = currentAccount.getFcmTokens();

		if (FcmToken.getToken(tokens, iidToken) == null) {
			tokens.add(new FcmToken(iidToken, userAgent));
			currentAccount.setFcmTokens(tokens);
			update(currentAccount);
		}
	}

	@Override
	public Account findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Boolean resetPassword(String webSafeKey) throws EntityValidationException, UniqueIndexConflictException {
		Account account = repository.findByWebSafeKey(webSafeKey);
		account.setPassword(passwordEncoder.encode("*"));
		repository.save(account);
		return true;
	}

	@Override
	public Boolean existsByEmail(String email) {
		Account account = findByEmail(email);
		if (account == null)
			return false;

		if (Strings.isNullOrEmpty(account.getEmail()))
			return false;

		return true;
	}

	@Override
	public Account create(final Account entity) throws Throwable {
		Account entity2 = repository.save(entity);
		return entity2;
	}

	@Override
	public Account update(final Account newEntity) throws Throwable {
		Account oldEntity = repository.findByWebSafeKey(newEntity.getWebSafeKey());
		try {
			if (oldEntity.getVersion() > newEntity.getVersion())
				throw new EntityVersionConflictException();
			return repository.save(checkForChanges(newEntity, oldEntity));
		} catch (Throwable e) {
			e.printStackTrace(System.out);
			throw new RuntimeException(e);
		}
	}
}
