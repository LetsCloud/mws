/**
 * 
 */
package io.crs.mws.client.core.message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CR
 *
 */
public class MessageData {
	private final String title;
	private final String description;
	private MessageStyle style;
	private MessageCloseDelay closeDelay;

	private List<MessageButtonConfig> buttonConfigs = new ArrayList<MessageButtonConfig>();

	public MessageData(String title, String description) {
		this.title = title;
		this.description = description;
		this.style = MessageStyle.SUCCESS;
		this.closeDelay = MessageCloseDelay.DEFAULT;
	}

	public MessageData(MessageStyle style, String title, String description) {
		this(title, description);
		this.style = style;
	}

	public MessageData(MessageStyle style, String title, String description, MessageCloseDelay closeDelay) {
		this(style, title, description);
		this.closeDelay = closeDelay;
	}

	public MessageStyle getStyle() {
		return style;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public MessageCloseDelay getCloseDelay() {
		return closeDelay;
	}

	public List<MessageButtonConfig> getBttonConfigs() {
		return buttonConfigs;
	}

	public void setBttonConfigs(List<MessageButtonConfig> buttonConfigs) {
		this.buttonConfigs = buttonConfigs;
	}

	public void addBttonConfig(MessageButtonConfig buttonConfig) {
		this.buttonConfigs.add(buttonConfig);
	}

	@Override
	public String toString() {
		return "MessageData [title=" + title + ", description=" + description + ", style=" + style + ", closeDelay="
				+ closeDelay + ", buttonConfigs=" + buttonConfigs + "]";
	}

}