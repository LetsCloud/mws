/**
 * 
 */
package io.crs.mws.server.repository.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;

import io.crs.mws.server.entity.BaseEntity;
import io.crs.mws.server.entity.ForeignKey;
import io.crs.mws.server.repository.CrudRepository;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.ForeignKeyConflictException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

/**
 * @author robi
 *
 */
public abstract class CrudRepositoryImpl<T extends BaseEntity> extends ObjectifyBaseRepository<T>
		implements CrudRepository<T> {
	private static final Logger logger = LoggerFactory.getLogger(CrudRepositoryImpl.class.getName());

	protected List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();

	protected CrudRepositoryImpl(Class<T> clazz) {
		super(clazz);
		logger.info("CrudRepositoryImpl");
	}

	protected abstract Object getParent(T entity);

	protected abstract Object getParentKey(String parentWebSafeKey);

	protected abstract void loadUniqueIndexMap(T entiy);

	@Override
	public T save(T entity) throws EntityValidationException, UniqueIndexConflictException {
		entity.validate();
		entity.clearUniqueIndexes();
		loadUniqueIndexMap(entity);
		checkUniqueIndexConflict(getParent(entity), entity);
		return putAndLoad(entity);
	}

	@Override
	public T findById(Long id) {
		return get(id);
	}

	@Override
	public T findByWebSafeKey(String webSafeKey) {
		return get(webSafeKey);
	}

	@Override
	public Boolean isExists(String property, Object value, Object parent) {
		logger.info("property=" + property);
		logger.info("value=" + value);
		logger.info("parent=" + parent);
		Key<T> key = getChildKeyByProperty(parent, property, value);
		if (key == null) {
			logger.info(value + " is not exist");
			return false;
		}
		logger.info("Entity is exist-> ");
		return true;
	}

	protected abstract void prepareForeignKeys(String webSafeKey);

	@Override
	public void delete(String webSafeKey) throws ForeignKeyConflictException {
		logger.info("delete->webSafeKey=" + webSafeKey);
		prepareForeignKeys(webSafeKey);
		for (ForeignKey foreignKey : foreignKeys) {
			logger.info("ForeignKey=" + foreignKey.getException());
			CrudRepository<?> repo = foreignKey.getRepo();
			if (repo.isExists(foreignKey.getProperty(), foreignKey.getValue(), foreignKey.getParent()))
				throw new ForeignKeyConflictException(foreignKey.getException());
		}
		delete(getKey(webSafeKey));
	}

	@Override
	public List<T> getAll() {
		return super.getAll();
	}

	@Override
	public List<T> getAll(Object parent) {
		return getChildren(parent);
	}

	@Override
	public void deleteAll(Object parent) {
		Boolean delete = true;
		while (delete) {
			List<Key<T>> keys = getChildrenKeys(parent);
			if (keys.isEmpty()) {
				delete = false;
			} else {
				deleteByKeys(keys);
			}
		}
	}

	@Override
	public List<T> getChildren(String parentWebSafeKey) {
		return getChildren(getParentKey(parentWebSafeKey));
	}

	@Override
	public List<T> getChildrenByFilters(String parentWebSafeKey, Map<String, Object> filters) {
		return getChildrenByFilters(getParentKey(parentWebSafeKey), filters);
	}

}
