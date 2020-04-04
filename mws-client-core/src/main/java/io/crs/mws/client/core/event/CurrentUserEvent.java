/**
 * 
 */
package io.crs.mws.client.core.event;

import java.util.logging.Logger;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author robi
 *
 */
public class CurrentUserEvent extends GwtEvent<CurrentUserEvent.CurrentUserHandler> {
	private static final Logger LOGGER = Logger.getLogger(CurrentUserEvent.class.getName());

	public interface CurrentUserHandler extends EventHandler {
		void onCurrentUserLogedIn(CurrentUserEvent event);
	}

	public static final Type<CurrentUserHandler> TYPE = new Type<>();

	private boolean loggedIn;

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public CurrentUserEvent(boolean loggedIn) {
		LOGGER.info("CurrentUserEvent()");
		this.loggedIn = loggedIn;
	}

	public static void fire(HasHandlers source, boolean loggedIn) {
		source.fireEvent(new CurrentUserEvent(loggedIn));
	}

	@Override
	public Type<CurrentUserHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<CurrentUserHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CurrentUserHandler handler) {
		handler.onCurrentUserLogedIn(this);
	}
}
