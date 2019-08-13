/**
 * 
 */
package io.crs.mws.shared.dto.auth;

import io.crs.mws.shared.cnst.SignupError;
import io.crs.mws.shared.dto.Dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class SignupResponse implements Dto {
	private boolean success;
	private SignupError errorCode;

	public SignupResponse() {
	}

	public SignupResponse(boolean success, SignupError errorCode) {
		this();
		this.success = success;
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public SignupError getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(SignupError errorCode) {
		this.errorCode = errorCode;
	}

}