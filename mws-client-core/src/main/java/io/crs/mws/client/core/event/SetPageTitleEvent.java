/**
 * 
 */
package io.crs.mws.client.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

import io.crs.mws.shared.cnst.MenuItemType;

/**
 * @author CR
 *
 */
public class SetPageTitleEvent extends GwtEvent<SetPageTitleEvent.SetPageTitleHandler> {

	public interface SetPageTitleHandler extends EventHandler {
		void onSetPageTitle(SetPageTitleEvent event);
	}

	public static final Type<SetPageTitleHandler> TYPE = new Type<>();

	private final String title;
	private final String description;
	private final MenuItemType menuItemType;

	public SetPageTitleEvent(String title, String description, MenuItemType menuItemType) {
		this.title = title;
		this.description = description;
		this.menuItemType = menuItemType;
	}

	public static void fire(String title, String description, MenuItemType menuItemType, HasHandlers source) {
		source.fireEvent(new SetPageTitleEvent(title, description, menuItemType));
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public MenuItemType getMenuItemType() {
		return menuItemType;
	}

	@Override
	public Type<SetPageTitleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SetPageTitleHandler handler) {
		handler.onSetPageTitle(this);
	}
}