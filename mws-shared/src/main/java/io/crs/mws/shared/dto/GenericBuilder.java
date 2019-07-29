/**
 * 
 */
package io.crs.mws.shared.dto;

import io.crs.mws.shared.dto.BaseDto.Builder;

/**
 * @author robi
 *
 */
public abstract class GenericBuilder <B extends GenericBuilder<B>> {

	private Long id;
	private String webSafeKey;

	public B id(Long id) {
		this.id = id;
		return self();
	}

	public B webSafeKey(String webSafeKey) {
		this.webSafeKey = webSafeKey;
		return self();
	}

    abstract BaseDto build();

    @SuppressWarnings("unchecked")
    final B self() {
        return (B) this;
    }
}
