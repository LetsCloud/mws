/**
 * 
 */
package io.crs.mws.client.core.event;

import java.util.logging.Logger;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author CR
 *
 */
public class ContentPushEvent extends GwtEvent<ContentPushEvent.ContentPushHandler> {
	private static final Logger LOGGER = Logger.getLogger(ContentPushEvent.class.getName());
	
	public enum MenuState { OPEN, CLOSE}

    public interface ContentPushHandler extends EventHandler {
        void onContentPush(ContentPushEvent event);
    }

    public static final Type<ContentPushHandler> TYPE = new Type<>();

    private MenuState menuState;
    
    public MenuState getMenuState() {
		return menuState;
	}

	public void setMenuState(MenuState menuState) {
		this.menuState = menuState;
	}

	public ContentPushEvent(MenuState menuState) {
		LOGGER.info("ContentPushEvent()");
		this.menuState = menuState;		
	}

    public static void fire(HasHandlers source, MenuState menuState) {
        source.fireEvent(new ContentPushEvent(menuState));
    }

    @Override
    public Type<ContentPushHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ContentPushHandler handler) {
        handler.onContentPush(this);
    }
}
