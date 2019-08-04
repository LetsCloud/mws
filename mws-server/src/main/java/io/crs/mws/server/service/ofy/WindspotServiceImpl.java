/**
 * 
 */
package io.crs.mws.server.service.ofy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.entity.Windspot;
import io.crs.mws.server.repository.WindspotRepository;
import io.crs.mws.server.service.WindspotService;

/**
 * @author robi
 *
 */
@Service
public class WindspotServiceImpl extends CrudServiceImpl<Windspot, WindspotRepository> implements WindspotService {
	private static final Logger logger = LoggerFactory.getLogger(WindspotServiceImpl.class);

	@Autowired
	WindspotServiceImpl(WindspotRepository repository) {
		super(repository);
		logger.info("WindspotServiceImpl(");
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
	public Windspot create(final Windspot entity) throws Throwable {
		Windspot entity2 = repository.save(entity);
		return entity2;
	}
}
