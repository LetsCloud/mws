/**
 * 
 */
package io.crs.mws.server.model;

/**
 * @author robi
 *
 */
public class FcmMessage {
	private String to;
	private FcmNotification notification;
	private FcmData data;

	public FcmMessage(FcmNotification notification) {
		this.notification = notification;
	}

	public FcmMessage(String token, FcmNotification notification) {
		this(notification);
		this.to = token;
	}

	public FcmMessage(String token, FcmData data) {
		this.to = token;
		this.data = data;
	}

	public FcmMessage(String token, FcmNotification notification, FcmData data) {
		this(token, notification);
		this.data = data;
	}

	public FcmNotification getNotification() {
		return notification;
	}

	public void setNotification(FcmNotification notification) {
		this.notification = notification;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String token) {
		this.to = token;
	}

	public FcmData getData() {
		return data;
	}

	public void setData(FcmData data) {
		this.data = data;
	}

}
