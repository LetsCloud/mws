/**
 * 
 */
package io.crs.mws.server.service.ofy;

import java.util.EnumSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.crs.mws.server.entity.GlobalConfig;
import io.crs.mws.server.repository.GlobalConfigRepository;
import io.crs.mws.server.service.GlobalConfigService;
import io.crs.mws.shared.cnst.GlobalParam;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

/**
 * @author robi
 *
 */
@Service
public class GlobalConfigServiceImpl extends CrudServiceImpl<GlobalConfig, GlobalConfigRepository>
		implements GlobalConfigService {
	private static final Logger logger = LoggerFactory.getLogger(GlobalConfigServiceImpl.class.getName());

	private static EnumSet<GlobalParam> allParams = EnumSet.of(GlobalParam.FB1_API_KEY, GlobalParam.FB2_AUTH_DOMAIN,
			GlobalParam.FB3_DATABASE_URL, GlobalParam.FB4_PROJECT_ID, GlobalParam.FB5_STORAGE_BUCKET,
			GlobalParam.FB6_MESSAGE_SENDER_ID);

	private final GlobalConfigRepository repository;

	@Autowired
	public GlobalConfigServiceImpl(GlobalConfigRepository repository) {
		super(repository);
		logger.info("GlobalConfigServiceImpl()");
		this.repository = repository;
	}

	@Override
	public void checkParams() {
		List<GlobalConfig> storedParams = getParams();

		for (GlobalParam param : allParams) {
			if (storedParams.stream().filter(p -> param.equals(p.getCode())).count() == 0)
				try {
					repository.save(new GlobalConfig(param));
				} catch (EntityValidationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UniqueIndexConflictException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public List<GlobalConfig> getParams() {
		return repository.getAll();
	}

	@Override
	public List<GlobalConfig> getAll() {
		checkParams();
		return super.getAll();
	}

	@Override
	protected List<Object> getParents(Long accountId) {
		return null;
	}

	@Override
	protected List<Object> getParents(String accountWebSafeKey) {
		return null;
	}

}
