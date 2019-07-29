/**
 * 
 */
package io.crs.mws.shared.exception;

import io.crs.mws.shared.dto.ErrorResponseDto;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class BaseException extends Exception implements HasErrorResponse {

	public BaseException() {
		super();
	}

	public BaseException(String string) {
		super(string);
	}

	@Override
	public ErrorResponseDto getErrorResponse() {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(ErrorTitleCode.UNKNOWN.toString() + "-" + getMessage());
		return error;
	}

}
