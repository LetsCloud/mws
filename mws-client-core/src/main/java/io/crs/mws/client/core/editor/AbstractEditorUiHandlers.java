/**
 * 
 */
package io.crs.mws.client.core.editor;

import com.gwtplatform.mvp.client.UiHandlers;

import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public interface AbstractEditorUiHandlers<T extends BaseDto> extends UiHandlers {

	void save(T dto);
	
	void cancel();
}
