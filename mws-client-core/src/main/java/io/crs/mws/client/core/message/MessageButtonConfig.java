/**
 * 
 */
package io.crs.mws.client.core.message;

import com.google.gwt.event.dom.client.ClickEvent;

import io.crs.mws.client.core.promise.xgwt.Fn;

/**
 * @author robi
 *
 */
public class MessageButtonConfig {

	private MessageButtonType type;
	private Fn.Arg<ClickEvent> callback;

	public MessageButtonConfig(MessageButtonType type, Fn.Arg<ClickEvent> callback) {
		super();
		this.type = type;
		this.callback = callback;
	}

	public MessageButtonType getType() {
		return type;
	}

	public void setType(MessageButtonType type) {
		this.type = type;
	}

	public Fn.Arg<ClickEvent> getCallback() {
		return callback;
	}

	public void setCallback(Fn.Arg<ClickEvent> callback) {
		this.callback = callback;
	}

}
