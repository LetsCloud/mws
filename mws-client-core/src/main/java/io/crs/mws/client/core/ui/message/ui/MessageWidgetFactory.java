/**
 * 
 */
package io.crs.mws.client.core.ui.message.ui;

import io.crs.mws.client.core.message.MessageData;

/**
 * @author CR
 *
 */
public interface MessageWidgetFactory {
	MessageWidget createMessage(MessageData message);
}