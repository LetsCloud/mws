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
public class ApiResponse implements Dto {
	private boolean success;
	private String message;

	public ApiResponse() {
	}

	public ApiResponse(boolean success, String message) {
		this();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}