/**
 * 
 */
package io.crs.mws.client.core.ui.filter;

import java.util.logging.Logger;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author robi
 *
 */
public class FilterChangeEvent extends GwtEvent<FilterChangeEvent.FilterChangeHandler> {
	private static final Logger logger = Logger.getLogger(FilterChangeEvent.class.getName());

	public enum DataTable {
		USER_GROUPS, APP_USER, TASK, QUOTATION, HOTEL, ROOM_TYPE
	}

	public interface FilterChangeHandler extends EventHandler {
		void onFilterChange(FilterChangeEvent event);
	}

	public static final Type<FilterChangeHandler> TYPE = new Type<>();

	private DataTable dataTable;

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public FilterChangeEvent(DataTable dataTable) {
		logger.info("FilterChangeEvent()");
		this.dataTable = dataTable;
	}

	public static void fire(HasHandlers source, DataTable dataTable) {
		source.fireEvent(new FilterChangeEvent(dataTable));
	}

	@Override
	public Type<FilterChangeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilterChangeHandler handler) {
		handler.onFilterChange(this);
	}
}
