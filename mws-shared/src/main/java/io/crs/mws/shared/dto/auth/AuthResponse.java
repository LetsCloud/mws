/**
 * 
 */
package io.crs.mws.shared.dto.auth;

import io.crs.mws.shared.dto.Dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class AuthResponse implements Dto {
	private String accessToken;
	private String tokenType = "Bearer";

	public AuthResponse() {
	}

	public AuthResponse(String accessToken) {
		this();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
