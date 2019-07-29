/**
 * 
 */
package io.crs.mws.server.entity;

import java.util.Date;

import io.crs.mws.shared.dto.Dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class FcmTokenDto implements Dto {

	private String token;

	private String userAgent;

	private Date created;

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

	@Override
	public String toString() {
		return "FcmTokenDto [token=" + token + ", userAgent=" + userAgent + ", created=" + created + "]";
	}

}
