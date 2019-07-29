/**
 * 
 */
package io.crs.mws.client.core.meditor;

import com.gwtplatform.mvp.client.UiHandlers;

import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public interface IsDtoEditor<T extends BaseDto> extends UiHandlers {

	void create();

	void edit(T dto);

	void save(T dto);
}
