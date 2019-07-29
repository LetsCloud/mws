/**
 * 
 */
package io.crs.mws.client.core.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

/**
 * @author CR
 *
 */
public interface CoreConstants extends Constants {

	Map<String, String> languageMap();

	Map<String, String> userPermMap();

	Map<String, String> taskKindMap();

	Map<String, String> taskStatusMap();

	Map<String, String> taskNoteTypeMap();

	Map<String, String> profileTypeMap();

	Map<String, String> communicationModeMap();

	Map<String, String> addressTypeMap();

	Map<String, String> webPresenceTypeMap();

	Map<String, String> inventoryTypeMap();

	Map<String, String> roomStatusMap();

	Map<String, String> erroCodeMap();

	Map<String, String> dialogButtonMap();

	Map<String, String> oooReturnWhenMap();
}
