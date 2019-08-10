/**
 * 
 */
package io.crs.mws.server.repository.ofy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.crs.mws.server.entity.Windspot;
import io.crs.mws.server.repository.WindspotRepository;
import io.crs.mws.shared.exception.cnst.ErrorMessageCode;

/**
 * @author robi
 *
 */
@Repository
public class WindspotRepositoryImpl extends CrudRepositoryImpl<Windspot> implements WindspotRepository {

	private static final String PROPERTY_NAME = "name";

	@Autowired
	WindspotRepositoryImpl() {
		super(Windspot.class);
	}

	@Override
	protected Object getParent(Windspot entity) {
		return null;
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
	protected void loadUniqueIndexMap(Windspot entiy) {
		if ((entiy.getName() != null) && (!entiy.getName().isEmpty()))
			entiy.addUniqueIndex(PROPERTY_NAME, entiy.getName(), ErrorMessageCode.APPUSER_CODE_ALREADY_EXISTS);
	}
}