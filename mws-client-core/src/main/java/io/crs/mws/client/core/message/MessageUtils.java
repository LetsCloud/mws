/**
 * 
 */
package io.crs.mws.client.core.message;

import java.util.Date;
import java.util.Map;

import io.crs.mws.client.core.i18n.CoreMessages;
import io.crs.mws.client.core.util.DateUtils;
import io.crs.mws.shared.exception.cnst.ErrorMessageCode;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;
import io.crs.mws.shared.exception.cnst.ValueCode;

/**
 * @author robi
 *
 */
public class MessageUtils {

	public static String tranlateTitle(CoreMessages i18n, ErrorTitleCode titleCode) {
		switch (titleCode) {
		case CRUD_CANNOT_BE_SAVED:
			return i18n.CRUD_CANNOT_BE_SAVED();
		case CRUD_CANNOT_BE_DELETED:
			return i18n.CRUD_CANNOT_BE_DELETED();
		default:
			return titleCode.toString();
		}
	}

	public static String tranlateMessage(CoreMessages i18n, ErrorMessageCode messageCode,
			Map<ValueCode, Object> valuesMap, String locale) {
		switch (messageCode) {
		case TASKGROUP_CODE_ALREADY_EXISTS:
			String value = (String) valuesMap.get(ValueCode.VC_STRING);
			return i18n.TASKGROUP_CODE_ALREADY_EXISTS(value);
		case TASKGROUP_ID_USED_BY_TASKTODO:
			return i18n.TASKGROUP_ID_USED_BY_TASKTODO();
		case TASKGROUP_ID_USED_BY_TASKTYPE:
			return i18n.TASKGROUP_ID_USED_BY_TASKTYPE();
		case OOO_ROOM_OVERLAP:
			String room = (String) valuesMap.get(ValueCode.VC_ROOM);
			long fromDate = (long) valuesMap.get(ValueCode.VC_FROM_DATE);
			long toDate = (long) valuesMap.get(ValueCode.VC_TO_DATE);
			return i18n.OOO_ROOM_OVERLAP(room, DateUtils.formatDateTime(new Date(fromDate), locale),
					DateUtils.formatDateTime(new Date(toDate), locale));
		default:
			return messageCode.toString();
		}
	}
}
