/**
 * 
 */
package io.crs.mws.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class BaseDto implements Dto {

	/**
	 * Generált ID amit az Objectify használ az entitás példány entitás típuson
	 * belüli azonosítására
	 */
	private Long id;

	/**
	 * Az entitás kulcsából képzett ID, amit a RequestFactory használ
	 */
	private String webSafeKey;

	/**
	 * A RequestFactory használja verzió követéshez
	 */
	private Long version = 0L;

	public BaseDto() {
	}

	public BaseDto(Long id) {
		this();
		this.id = id;
	}

	public BaseDto(BaseDto source) {
		this();
		this.id = source.getId();
		this.version = source.getVersion();
		this.webSafeKey = source.getWebSafeKey();
	}

	public BaseDto(Builder<?> builder) {
		id = builder.id;
		version = builder.version;
		webSafeKey = builder.webSafeKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWebSafeKey() {
		return webSafeKey;
	}

	public void setWebSafeKey(String webSafeKey) {
		this.webSafeKey = webSafeKey;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		BaseDto other = (BaseDto) obj;

		if (webSafeKey == null) {
			if (other.webSafeKey != null) {
				return false;
			}
		} else if (!webSafeKey.equals(other.webSafeKey)) {
			return false;
		}

		return true;
	}

	@JsonIgnore
	public boolean isSaved() {
		return webSafeKey != null;
	}

	@Override
	public String toString() {
		String ret = "BaseDto:{id=" + id + ", webSafeKey=" + webSafeKey + ", version=" + version + "}";
		return ret;
	}

	/**
	 * 
	 * @author robi
	 *
	 * @param <T>
	 */
	public static abstract class Builder<T extends Builder<T>> {

		private Long id;
		private Long version;
		private String webSafeKey;

		protected abstract T self();

		public T id(Long id) {
			this.id = id;
			return self();
		}

		public T version(Long version) {
			this.version = version;
			return self();
		}

		public T webSafeKey(String webSafeKey) {
			this.webSafeKey = webSafeKey;
			return self();
		}

		public BaseDto build() {
			return new BaseDto(this);
		}
	}

	/**
	 * 
	 * @author robi
	 *
	 */
	private static class Builder2 extends Builder<Builder2> {
		@Override
		protected Builder2 self() {
			return this;
		}
	}

	public static Builder<?> builder() {
		return new Builder2();
	}
}
