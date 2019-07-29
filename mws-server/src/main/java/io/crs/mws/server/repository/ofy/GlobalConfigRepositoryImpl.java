/**
 * 
 */
package io.crs.mws.server.repository.ofy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import io.crs.mws.server.entity.GlobalConfig;
import io.crs.mws.server.repository.GlobalConfigRepository;

/**
 * @author robi
 *
 */
@Repository
public class GlobalConfigRepositoryImpl extends CrudRepositoryImpl<GlobalConfig> implements GlobalConfigRepository {
	private static final Logger logger = LoggerFactory.getLogger(GlobalConfigRepositoryImpl.class.getName());

	public GlobalConfigRepositoryImpl() {
		super(GlobalConfig.class);
		logger.info("GlobalConfigRepositoryImpl()");
	}

	@Override
	protected Object getParent(GlobalConfig entity) {
		return null;
	}

	@Override
	protected Object getParentKey(String parentWebSafeKey) {
		return null;
	}

	@Override
	protected void loadUniqueIndexMap(GlobalConfig entiy) {
	}

	@Override
	protected void prepareForeignKeys(String webSafeKey) {
		// TODO Auto-generated method stub
		
	}

}
