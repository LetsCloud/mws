/**
 * 
 */
package io.crs.mws.client.core.message.callback;

import java.util.logging.Logger;

import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;

import io.crs.mws.client.core.event.DisplayMessageEvent.MessageTarget;
import io.crs.mws.client.core.i18n.CoreMessages;
import io.crs.mws.shared.exception.cnst.ErrorMessageCode;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;

/**
 * @author CR
 *
 */
public abstract class ErrorHandlerAsyncCallback<R> implements AsyncCallback<R> {
	private static Logger logger = Logger.getLogger(ErrorHandlerAsyncCallback.class.getName());

	private final HasHandlers hasHandlers;
	private final MessageTarget target;
	private final CoreMessages i18n;

	public ErrorHandlerAsyncCallback(HasHandlers hasHandlers, MessageTarget target, CoreMessages i18n) {
		logger.info("ErrorHandlerAsyncCallback()");
		this.hasHandlers = hasHandlers;
		this.target = target;
		this.i18n = i18n;
	}

	@Override
	public void onFailure(Throwable caught) {
/*		if (caught instanceof CustomActionException) {
			CustomActionException exception = (CustomActionException) caught;
			MessageData message = new MessageData(MessageStyle.ERROR,
					translateTitle(exception.getErrorResponse().getTitleCode()), translateDescription(
							exception.getErrorResponse().getMessageCode(), exception.getErrorResponse().getProperty()));
			logger.info("ErrorHandlerAsyncCallback().onFailure()->target=" + target);
			DisplayMessageEvent.fire(hasHandlers, target, message);
		}
*/		
	}

	private String translateTitle(ErrorTitleCode exception) {
		switch (exception) {
		case CRUD_CANNOT_BE_SAVED:
			return i18n.CRUD_CANNOT_BE_SAVED();
		case CRUD_CANNOT_BE_DELETED:
			return i18n.CRUD_CANNOT_BE_DELETED();
		default:
			return exception.toString();
		}

	}

	private String translateDescription(ErrorMessageCode exception, String value) {
		switch (exception) {
		case TASKGROUP_CODE_ALREADY_EXISTS:
			return i18n.TASKGROUP_CODE_ALREADY_EXISTS(value);
		case TASKGROUP_ID_USED_BY_TASKTODO:
			return i18n.TASKGROUP_ID_USED_BY_TASKTODO();
		case TASKGROUP_ID_USED_BY_TASKTYPE:
			return i18n.TASKGROUP_ID_USED_BY_TASKTYPE();
		default:
			return exception.toString();
		}
	}
}
