/**
 * 
 */
package io.crs.mws.server.model;

/**
 * @author CR
 *
 */
public class FcmNotification {

	private String title;
	private String body;
	private String icon;
	private String click_action;

	public FcmNotification(String title, String body, String icon) {
		this.title = title;
		this.body = body;
		this.icon = icon;
	}

	public FcmNotification(String title, String body, String icon, String click_action) {
		this(title, body, icon);
		this.click_action = click_action;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getClick_action() {
		return click_action;
	}

	public void setClick_action(String click_action) {
		this.click_action = click_action;
	}

	@Override
	public String toString() {
		return "FcmNotification:{title=" + title + ", body=" + body + ", icon=" + icon + ", click_action="
				+ click_action + "}";
	}
}
