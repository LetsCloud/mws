/**
 * 
 */
package io.crs.mws.client.core.meditor;

import com.gwtplatform.mvp.client.View;

import io.crs.mws.client.core.message.MessageData;
import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public interface MeditorView<T extends BaseDto> extends View {

	void open(T dto);

	void showMessage(MessageData message);

	void close();

}
