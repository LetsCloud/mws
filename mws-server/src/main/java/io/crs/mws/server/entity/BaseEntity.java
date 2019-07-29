/**
 * 
 */
package io.crs.mws.server.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.OnSave;

import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.cnst.ErrorMessageCode;

/**
 * Minden entitás őse.
 * <p>
 * Tartalmazza az entitás fajtán belőli egyedi azonosítót (id), az entitás
 * adatbázison belüli egyedi kulcsát (webSafeKey) és az entitás verziószámát.
 * 
 * @author robi
 *
 */
public abstract class BaseEntity {

	/**
	 * Generált ID amit az Objectify használ az entitás példány entitás típuson
	 * belüli azonosítására
	 */
	@Id
	private Long id;

	/**
	 * Az entitás adatbázison belüli egyedi kulcsa. Tranzitív.
	 */
	@Ignore
	private String webSafeKey;

	/**
	 * Az entitás verzió követését szolgálja
	 */
	private Long version = 0L;

	/**
	 * Egyedi indexek ellenőrzését szolgáló leképezés.
	 * <p>
	 * A leképezés kulcsa a mező neve, értéke pedig a mező értéke.
	 */
	@Ignore
	private List<UniqueKey> uniqueIndexes = new ArrayList<UniqueKey>();

//	private Map<String, Object> uniqueIndexes = new HashMap<String, Object>();
	/**
	 * Az entitás verziiószámát növelő trigger, amely az entitás mentése előtt fut
	 * le.
	 */
	@OnSave
	private void onSave() {
		this.version++;
	}

	/**
	 * Az entitás betöltésekor lefutő trigger metódus, amely letrehozza az entitás
	 * egyedi kulcsát.
	 */
	@OnLoad
	private void onLoad() {
		Key<BaseEntity> key = Key.create(this);
		this.webSafeKey = key.getString();
//		logger.info("onLoad()->id=" + id);
//		logger.info("onLoad()->webSafeKey=" + webSafeKey);
	}

	/**
	 * Objectify miatt szükséges.
	 */
	public BaseEntity() {
//		logger.info("BaseEntity()");
	}

	public BaseEntity(BaseEntity source) {
		id = source.id;
		version = source.version;
		webSafeKey = source.webSafeKey;
		uniqueIndexes = source.uniqueIndexes;
	}

	/**
	 * Entitás fajtán belüli egyedi azonosító visszaadása.
	 * 
	 * @return Egyedi azonosíító.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Entitás fajtán belüli egyedi azonosító beállítása, amire entitás másolásakor
	 * ven szükséd.
	 * 
	 * @param id Egyedi azonosíító.
	 */
	public void setId(Long id) {
//		logger.info("setId()->" + id);
		this.id = id;
	}

	/**
	 * Adatbázison belüli egyedi kulcs létrehozása.
	 * 
	 * @return Egyedi kulcs.
	 */
	public String getWebSafeKey() {
		return webSafeKey;
	}

	/**
	 * Egyedi kulcs beállítása, amelyre entitás másolás esetén van szükség.
	 * 
	 * @param webSafeKey A beállítandó egyedi kulcs.
	 */
	public void setWebSafeKey(String webSafeKey) {
		this.webSafeKey = webSafeKey;
	}

	/**
	 * Entitás verziószám visszaadása.
	 * 
	 * @return Verziószám.
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Entitás verziószám beállítása, amelyre entitás másaolása esetén van szükség.
	 * 
	 * @param version Beállítandó verziószám.
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Az egyedi indexek ellenőrzéséhez szükséges értékek tárolójának kiűrítése.
	 */
	public void clearUniqueIndexes() {
		uniqueIndexes.clear();
	}

	/**
	 * Az egyedi indexek ellenőrzéséhez szükséges mező név és mező érték pár
	 * megadása.
	 * 
	 * @param property Mező név.
	 * @param value    Mező érték.
	 */
	public void addUniqueIndex(String property, Object value, ErrorMessageCode exception) {
		uniqueIndexes.add(new UniqueKey(property, value, exception));
	}

	/**
	 * Az egyedi indexek ellenőrzéséhez szükséges mező név és mező érték párok
	 * visszaadása.
	 * 
	 * @return Mező nevek és értékek leképezése.
	 */
	public List<UniqueKey> getUniqueIndexes() {
		return uniqueIndexes;
	}

	/**
	 * Validációs metódus, amelyet a
	 * {@link io.crs.mws.server.repository.ofy.CrudRepositoryImpl} osztály
	 * {@link io.crs.mws.server.repository.ofy.CrudRepositoryImpl#save(BaseEntity)}
	 * save metódusa hívja meg még az egyedi kulcsok értékesítés és az entitás
	 * mentése előtt.
	 * <p>
	 * Validációs hiba esetén EntityValidationException kivételt dob.
	 * 
	 * @throws EntityValidationException hibát dob
	 */
	public void validate() throws EntityValidationException {
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", webSafeKey=" + webSafeKey + ", version=" + version + ", uniqueIndexes="
				+ uniqueIndexes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((webSafeKey == null) ? 0 : webSafeKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (webSafeKey == null) {
			if (other.webSafeKey != null)
				return false;
		} else if (!webSafeKey.equals(other.webSafeKey))
			return false;
		return true;
	}

}
