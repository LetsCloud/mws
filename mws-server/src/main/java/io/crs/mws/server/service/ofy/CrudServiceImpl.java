/**
 * 
 */
package io.crs.mws.server.service.ofy;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Work;

import io.crs.mws.server.entity.BaseEntity;
import io.crs.mws.server.repository.CrudRepository;
import io.crs.mws.server.service.CrudService;
import io.crs.mws.shared.exception.EntityVersionConflictException;
import io.crs.mws.shared.exception.ForeignKeyConflictException;

/**
 * @author robi
 *
 */
public abstract class CrudServiceImpl<T extends BaseEntity, R extends CrudRepository<T>> implements CrudService<T> {
	private static final Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class.getName());

	protected R repository;

	public CrudServiceImpl(R repository) {
		logger.info("CrudServiceImpl");
		this.repository = repository;
	}

	public R getRepository() {
		return repository;
	}
	
	public T findByWebSafeKey(String webSafeKey) {
		return repository.findByWebSafeKey(webSafeKey);
	}

	public T findById(Long id) {
		return repository.findById(id);
	}

	protected abstract List<Object> getParents(Long accountId);

	protected abstract List<Object> getParents(String accountWebSafeKey);

	@Override
	public T create(final T entity) throws Throwable {
		// A tranzakció végrehajtása folyamán jelentkező kivétel elfogása
		// céljából...
		try {
			// Objectify tranzakció indul
			T th = ofy().transact(new Work<T>() {
				public T run() {
					try {
						// majd megpróbáljuk elmenteni
						T entity2 = repository.save(entity);
						return entity2;
					} catch (Throwable e) {
						// Kivétel esetén csomagoljuk azt egy futásidejű
						// kivételbe, majd kiváltjuk
						throw new RuntimeException(e);
					}
				}
			});
			return th;
		} catch (RuntimeException re) {
			// A csomagolt kivételt elcsípjük és továbbküldjük
			throw re.getCause();
		}
	}

	@Override
	public T read(String webSafeKey) throws Throwable {
		return repository.findByWebSafeKey(webSafeKey);
	}

	protected T checkForChanges(T newEntity, T oldEntity) {
		return newEntity;
	}

	@Override
	public T update(final T newEntity) throws Throwable {
		try {
			T th = ofy().transact(new Work<T>() {
				public T run() {
					T oldEntity = repository.findByWebSafeKey(newEntity.getWebSafeKey());
					try {
						if (oldEntity.getVersion() > newEntity.getVersion())
							throw new EntityVersionConflictException();
						return repository.save(checkForChanges(newEntity, oldEntity));
					} catch (Throwable e) {
						e.printStackTrace(System.out);
						throw new RuntimeException(e);
					}
				}
			});
			return th;
		} catch (RuntimeException re) {
			throw re.getCause();
		}
	}

	protected void checkDifference(T oldEntity, T newEntity) {
	}

	@Override
	public Boolean delete(String webSafeKey) throws ForeignKeyConflictException {
		repository.delete(webSafeKey);
		return true;
	}

	@Override
	public List<T> getAll() {
		return repository.getAll();
	}

	@Override
	public List<T> getAll(Long accountId) {
		List<Object> parents = getParents(accountId);
		return getAllChildren(parents);
	}

	@Override
	public List<T> getAll(String accountWebSafeKey) {
		List<Object> parents = getParents(accountWebSafeKey);
		return getAllChildren(parents);
	}

	private List<T> getAllChildren(List<Object> parents) {
		List<T> entities = new ArrayList<T>();
		for (Object parent : parents)
			entities.addAll(repository.getAll(parent));
		return entities;
	}

	@Override
	public void deleteAll(Long accountId) {
		List<Object> parents = getParents(accountId);
		for (Object parent : parents)
			repository.deleteAll(parent);
	}

	@Override
	public List<T> getChildren(String parentWebSafeKey) {
		return repository.getChildren(parentWebSafeKey);
	}

	@Override
	public List<T> getChildrenByFilters(String parentWebSafeKey, Map<String, Object> filters) {
		if ((filters == null) || (filters.isEmpty())) {
			return repository.getChildren(parentWebSafeKey);
		}
		return repository.getChildrenByFilters(parentWebSafeKey, filters);
	}

}
