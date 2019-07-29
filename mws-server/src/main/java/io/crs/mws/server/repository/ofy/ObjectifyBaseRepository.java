/**
 * 
 */
package io.crs.mws.server.repository.ofy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import io.crs.mws.server.entity.BaseEntity;
import io.crs.mws.server.entity.UniqueKey;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * @author robi
 *
 */
public abstract class ObjectifyBaseRepository<T extends BaseEntity> {
	private static final Logger logger = LoggerFactory.getLogger(ObjectifyBaseRepository.class.getName());

	private final Class<T> clazz;

	protected ObjectifyBaseRepository(final Class<T> clazz) {
		logger.info("ObjectifyBaseRepository()");
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	/**
	 * Elmenti az entitást és visszaadja az egyedi kulcsát
	 * 
	 * @param entity
	 * @return
	 */
	public Key<T> put(T entity) {
		return ofy().save().entity(entity).now();
	}

	/**
	 * Elmenti az entitást, majd visszaadja a visszaolvasott értéket
	 * 
	 * @param entity
	 * @return
	 */
	public T putAndLoad(T entity) {
		Key<T> key = put(entity);
		entity = get(key);
		// You should assign webSafeKey manually, because the @Load method does not run
		// because the get method works from the cache.
		entity.setWebSafeKey(key.getString());
		while (entity == null) {
			entity = get(key);
		}
		return entity;
	}

	/**
	 * Elmenti az entitás gyűjteményt, majd visszaadja a visszaolvasott értékeket
	 * 
	 * @param entities
	 * @return
	 */
	public Collection<T> put(Iterable<T> entities) {
		return ofy().save().entities(entities).now().values();
	}

	/*************************************
	 * 
	 * GET
	 * 
	 ************************************/

	/**
	 * Kulcs alapján visszaadja az entitást
	 * 
	 * @param key
	 * @return
	 */
	public T get(Key<T> key) {
		return ofy().load().key(key).now();
	}

	/**
	 * Id alapján visszaadja az entitást
	 * 
	 * @param id
	 * @return
	 */
	public T get(Long id) {
		// work around for objectify cacheing and new query not having the
		// latest
		// data
		ofy().clear();

		return ofy().load().type(clazz).id(id).now();
	}

	/**
	 * 
	 * @param webSafeKey
	 * @return
	 */
	public T get(String webSafeKey) {
		try {
			Key<T> key = Key.create(webSafeKey);
			return ofy().load().key(key).now();
		} catch (IllegalArgumentException e) {
		}
		return null;
	}

	/**
	 * Visszaadja az entitás kulcsát a generált Id alapján
	 * 
	 * @param entity
	 * @return
	 */
	public Key<T> getKey(T entity) {
		return Key.create(clazz, entity.getWebSafeKey());
	}

	public Key<T> getKey(Long generatedId) {
		return Key.create(clazz, generatedId);
	}

	/**
	 * Visszaadja az entitás kulcsát a generált Id alapján
	 * 
	 * @param webSafeString
	 * @return
	 */
	public Key<T> getKey(String webSafeString) {
		return Key.create(webSafeString);
	}

	public Key<T> getKey(Key<?> parent, Long generatedId) {
		return Key.create(parent, clazz, generatedId);
	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public List<T> getList(List<Key<T>> keys) {
		return Lists.newArrayList(ofy().load().keys(keys).values());
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public Collection<T> getSubset(List<Long> ids) {
		return ofy().load().type(clazz).ids(ids).values();
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public Map<Long, T> getSubsetMap(List<Long> ids) {
		return ofy().load().type(clazz).ids(ids);
	}

	/**
	 * Visszadja az összes entitást
	 * 
	 * @return
	 */
	public List<T> getAll() {
		return ofy().load().type(clazz).list();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exists(Key<T> key) {
		return get(key) != null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Boolean exists(Long id) {
		return get(id) != null;
	}

	/*************************************
	 * 
	 * DELETE
	 * 
	 ************************************/

	/**
	 * Entitás törlés
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		ofy().delete().entity(entity).now();
	}

	/**
	 * Entitás törlés kulcs segítségével
	 * 
	 * @param key
	 */
	public void delete(Key<T> key) {
		ofy().delete().entity(key).now();
	}

	/**
	 * Entitások törlése
	 * 
	 * @param entities
	 */
	public void delete(List<T> entities) {
		ofy().delete().entities(entities).now();
	}

	/**
	 * Entitások törlése
	 * 
	 * @param keys
	 */
	public void deleteByKeys(List<Key<T>> keys) {
		ofy().delete().entities(keys).now();
	}

	/*************************************
	 * 
	 * QUERIES
	 * 
	 ************************************/

	/**
	 * 
	 * @return
	 */
	protected Query<T> query() {
		return ofy().load().type(clazz);
	}

	/**
	 * 
	 * @param query
	 * @param filters
	 * @return
	 */
	protected Query<T> addFilters(Query<T> query, Map<String, Object> filters) {
		Iterator<Entry<String, Object>> iterator = filters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			query = query.filter(entry.getKey(), entry.getValue());
		}
		return query;
	}

	/*************************************
	 * 
	 * GET ENTITY/S
	 * 
	 ************************************/

	/**
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public T getFirstByProperty(String propName, Object propValue) {
		T entity = query().filter(propName + " =", propValue).first().now();
		return entity;
	}

	/**
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public List<T> getByProperty(String propName, Object propValue) {
		List<T> list = query().filter(propName + " =", propValue).list();
		return list;
	}

	/*************************************
	 * 
	 * GET KEY/S
	 * 
	 ************************************/

	/**
	 * A
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public Key<T> getFirstKeyByProperty(String propName, Object propValue) {
		Key<T> key = query().filter(propName + " =", propValue).keys().first().now();
		return key;
	}

	public List<Key<T>> getKeysByProperty(String propName, Object propValue) {
		List<Key<T>> keys = query().filter(propName + " =", propValue).keys().list();
		return keys;
	}

	/**
	 * 
	 * @param filters
	 * @return
	 */
	public List<Key<T>> getKeysByFilters(Map<String, Object> filters) {
		Query<T> q = query();
		q = addFilters(q, filters);
		return q.keys().list();
	}

	/*************************************
	 * 
	 * GET CHILDREN
	 * 
	 ************************************/

	/**
	 * Visszaadja a szülő entitás összes gyermekét.
	 * 
	 * @param parent Szülő entitás
	 * @return
	 */
	public List<T> getChildren(Object parent) {
		return query().ancestor(parent).list();
	}

	/**
	 * Visszaadja a szülő entitás megadott feltételnek megfelelő első gyermekét.
	 * 
	 * @param parent    Szülő entitás
	 * @param propName  Feltétel tulajdonság neve
	 * @param propValue Feltétel tulajdonság értéke
	 * @return
	 */
	public T getChildByProperty(Object parent, String propName, Object propValue) {
		return query().ancestor(parent).filter(propName + " =", propValue).first().now();
	}

	/**
	 * Visszaadja a szülő entitás megadott feltételnek megfelelő összes gyermeket.
	 * 
	 * @param parent    Szülő entitás
	 * @param propName  Feltétel tulajdonság neve
	 * @param propValue Feltétel tulajdonság értéke
	 * @return
	 */
	public List<T> getChildrenByProperty(Object parent, String propName, Object propValue) {
		return query().ancestor(parent).filter(propName + " =", propValue).list();
	}

	/**
	 * Visszaadja a szülő entitás megadott feltételeknek megfelelő összes gyermeket.
	 * 
	 * @param parent  Szülő entitás
	 * @param filters Feltételek
	 * @return
	 */
	public List<T> getChildrenByFilters(Object parent, Map<String, Object> filters) {
		Query<T> q = query().ancestor(parent);
		q = addFilters(q, filters);
		return q.list();
	}

	/**
	 * Visszaadja a szülő entitás megadott feltételeknek valamint a megadott
	 * tulajdonság MAXimum értékének megfelő gyermekét.
	 * 
	 * @param parent   Szülő entitás
	 * @param property Tulajdonság
	 * @param filters  Feltételek
	 * @return
	 */
	public T getChildByMaxProperty(Object parent, String property, Map<String, Object> filters) {
		return getChildByMinProperty(parent, "-" + property, filters);
	}

	/**
	 * Visszaadja a szülő entitás megadott feltételeknek valamint a megadott
	 * tulajdonság MINimum értékének megfelő gyermekét.
	 * 
	 * @param parent Szülő entitás
	 * @return
	 */
	public T getChildByMinProperty(Object parent, String property, Map<String, Object> filters) {
		if (filters == null)
			return query().ancestor(parent).order(property).first().now();
		else {
			Query<T> q = query().ancestor(parent);
			q = addFilters(q, filters);
			return q.order(property).first().now();
		}
	}

	/*************************************
	 * 
	 * GET CHILDREN KEYS
	 * 
	 ************************************/

	/**
	 * 
	 * @param parent
	 * @return
	 */
	public List<Key<T>> getChildrenKeys(Object parent) {
		return query().limit(5000).ancestor(parent).keys().list();
	}

	/**
	 * 
	 * @param parent
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public Key<T> getChildKeyByProperty(Object parent, String propName, Object propValue) {
		return query().ancestor(parent).filter(propName + " =", propValue).keys().first().now();
	}

	/**
	 * 
	 * @param parent
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public List<Key<T>> getChildrenKeysByProperty(Object parent, String propName, Object propValue) {
		return query().ancestor(parent).filter(propName + " = ", propValue).keys().list();
	}

	/**
	 * 
	 * @param parent
	 * @param filters
	 * @return
	 */
	public List<Key<T>> getChildrenKeysByFilters(Object parent, Map<String, Object> filters) {
		Query<T> q = query().ancestor(parent);
		q = addFilters(q, filters);
		return q.keys().list();
	}

	public Long getGeneratedId(String webSafeString) {
		Key<T> key = getKey(webSafeString);
		return key.getId();
	}

	/**
	 * Egyedi kulcs ütközések vizsgálata.
	 * 
	 * @param parent Gyermek entitás esetén szüksége van a szülő kulcsára.
	 * @param entity Az új vagy módosítandó entitás.
	 * @throws UniqueIndexConflictException
	 */
	public void checkUniqueIndexConflict(Object parent, T entity) throws UniqueIndexConflictException {
//		logger.info("checkUniqueIndexConflict()->entity" + entity);
		// Az entitás egyedi indexeinek begyűjtése
		List<UniqueKey> uniqueIndexes = entity.getUniqueIndexes();
		// A egyedi indexek végigpásztázása
		for (UniqueKey uniqueindex : uniqueIndexes) {
			// Az egyedi kulcs ütközés vizsgálata
			if (isUniqueIndexConflict(parent, entity.getWebSafeKey(), uniqueindex.getProperty(),
					uniqueindex.getValue())) {
				throw new UniqueIndexConflictException(uniqueindex.getException(), entity.getClass().getSimpleName(),
						uniqueindex.getProperty(), uniqueindex.getValue());
			}
		}
	}

	/**
	 * Egy egyedi kulcs ütközés vizsgálata.
	 * 
	 * @param parent     Gyermek entitás esetén szüksége van a szülő kulcsára.
	 * @param webSafeKey Az új vagy módosítandó entitás azonosítója.
	 * @param property   Az egyedi index mőjének neve.
	 * @param value      A vizsgált egyedi kulcsértéke.
	 * @return A vizsgálat eredménye true=van ütközés, false=nincs ütközés
	 */
	public Boolean isUniqueIndexConflict(Object parent, String webSafeKey, String property, Object value) {
		// Kikeressük az egyedi értéknek megfelelő megfelelő entitás(ok)
		// kulcsait
		List<Key<T>> keys = null;
		if (parent == null)
			keys = getKeysByProperty(property, value);
		else
			keys = getChildrenKeysByProperty(parent, property, value);

		// Ha nincsen ilyen entitás, akkor kulcs ütközés sincs
		if ((keys == null) || (keys.isEmpty())) {
			logger.info("isUniqueIndexConflict()->1");
			return false;
		}

		// Ha rögzítendő entitás azonosítója null, akkor az entitás új és
		// ütközik az előzőleg megtalált entitással.
		if ((webSafeKey == null) && (keys.size() > 0)) {
			logger.info("isUniqueIndexConflict()->2");
			return true;
		}

		// Ha egyedül a módosítandó entitás azonosítója található meg a
		// találatok között, akkor nincs szó ütközésről.
		if ((keys.contains(Key.create(webSafeKey))) && (keys.size() == 1)) {
			logger.info("isUniqueIndexConflict()->3");
			return false;
		}

		// Egyébként egyedi kulcs ütközésrül van szó
		logger.info("isUniqueIndexConflict()->4");
		return true;
	}
}