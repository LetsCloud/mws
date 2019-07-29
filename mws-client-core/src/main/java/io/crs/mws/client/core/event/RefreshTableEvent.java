/**
 * 
 */
package io.crs.mws.client.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author robi
 *
 */
public class RefreshTableEvent extends GwtEvent<RefreshTableEvent.RefreshTableHandler> {

	public enum TableType {
		APP_USER, USER_GROUP, TASK_GROUP, TASK_TODO, TASK_TYPE, PROFILE_GROUP, RELATIONSHIP, QUOTATION_STATUS,
		ORGANIZATION, QUOTATION, CONTACT, MARKET_GROUP, HOTEL, ROOM_TYPE, ROOM;
	}

	public interface RefreshTableHandler extends EventHandler {
		void onRefresh(RefreshTableEvent event);
	}

	public static final Type<RefreshTableHandler> TYPE = new Type<>();

	private TableType tableType;

	RefreshTableEvent(TableType tableType) {
		this.tableType = tableType;
	}

	public static Type<RefreshTableHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, TableType tableType) {
		source.fireEvent(new RefreshTableEvent(tableType));
	}

	@Override
	public Type<RefreshTableHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefreshTableHandler handler) {
		handler.onRefresh(this);
	}

	public TableType getTableType() {
		return tableType;
	}
}