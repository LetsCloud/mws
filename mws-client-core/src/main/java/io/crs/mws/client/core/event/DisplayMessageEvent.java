/**
 * 
 */
package io.crs.mws.client.core.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

import io.crs.mws.client.core.message.MessageData;

/**
 * @author CR
 *
 */
public class DisplayMessageEvent extends GwtEvent<DisplayMessageEvent.DisplayMessageHandler> {

	public enum MessageTarget {
		APP_USER, USER_GROUP, TASK_GROUP, TASK_TODO, TASK_TYPE, PROFILE_GROUP, RELATIONSHIP, QUOTATION_STATUS,
		ORGANIZATION, QUOTATION, CONTACT, MARKET_GROUP, HOTEL, ROOM_TYPE, ROOM, OOO_ROOM_CREATE;
	}

	public interface DisplayMessageHandler extends EventHandler {
		void onDisplayMessage(DisplayMessageEvent event);
	}

	public static final Type<DisplayMessageHandler> TYPE = new Type<>();

	private MessageTarget target;

	private MessageData message;

	DisplayMessageEvent(MessageTarget target, MessageData message) {
		this.target = target;
		this.message = message;
	}

	public static Type<DisplayMessageHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, MessageTarget target, MessageData message) {
		source.fireEvent(new DisplayMessageEvent(target, message));
	}

	@Override
	public Type<DisplayMessageHandler> getAssociatedType() {
		return TYPE;
	}

	public MessageTarget getTarget() {
		return target;
	}

	public MessageData getMessage() {
		return message;
	}

	@Override
	protected void dispatch(DisplayMessageHandler handler) {
		handler.onDisplayMessage(this);
	}
}