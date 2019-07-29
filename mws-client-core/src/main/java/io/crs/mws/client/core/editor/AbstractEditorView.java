/**
 * 
 */
package io.crs.mws.client.core.editor;

import com.gwtplatform.mvp.client.View;

import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public interface AbstractEditorView<T extends BaseDto> extends View {
	
	void show(T dto);
	
	void edit(T dto);

	void close();

}
