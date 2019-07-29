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
public class FirebaseEvent extends GwtEvent<FirebaseEvent.FirebaseEventHandler> {
	private static final Logger LOGGER = Logger.getLogger(FirebaseEvent.class.getName());

	public interface FirebaseEventHandler extends EventHandler {
		void onInitialize(FirebaseEvent event);
	}

	public static final Type<FirebaseEventHandler> TYPE = new Type<>();

	private boolean initialized;

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public FirebaseEvent(boolean initialized) {
		LOGGER.info("CurrentUserEvent()");
		this.initialized = initialized;
	}

	public static void fire(HasHandlers source, boolean initialized) {
		source.fireEvent(new CurrentUserEvent(initialized));
	}

	@Override
	public Type<FirebaseEventHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<FirebaseEventHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FirebaseEventHandler handler) {
		handler.onInitialize(this);
	}
}
