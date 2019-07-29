package io.crs.mws.shared.dto;

import java.util.Date;

@SuppressWarnings("serial")
public class FcmTokenDto implements Dto {

	private String token;

	private String userAgent;

	private Date created;

	public FcmTokenDto() {

	}

	public FcmTokenDto(String token, String userAgent) {
		this.token = token;
		this.userAgent = userAgent;
		this.created = new Date();

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
