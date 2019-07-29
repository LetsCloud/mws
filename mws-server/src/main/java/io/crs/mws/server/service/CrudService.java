/**
 * 
 */
package io.crs.mws.server.service;

import java.util.List;
import java.util.Map;

import io.crs.mws.server.entity.BaseEntity;

/**
 * @author robi
 *
 */
public interface CrudService<T extends BaseEntity> {

	T findById(Long id);

	T findByWebSafeKey(String webSafeKey);
	
	T create(T entity) throws Throwable;

	T read(String webSafeKey) throws Throwable;

	T update(T entity) throws Throwable;

	Boolean delete(String id) throws Throwable;

	List<T> getAll();

	List<T> getAll(Long accountId);

	List<T> getAll(String accountWebSafeKey);

	List<T> getChildren(String parentWebSafeKey);

	List<T> getChildrenByFilters(String parentWebSafeKey, Map<String, Object> filters);

	void deleteAll(Long accountId);
}
