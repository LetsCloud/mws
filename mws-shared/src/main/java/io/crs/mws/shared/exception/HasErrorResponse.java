/**
 * 
 */
package io.crs.mws.shared.exception;

import io.crs.mws.shared.dto.ErrorResponseDto;

/**
 * @author robi
 *
 */
public interface HasErrorResponse {
	ErrorResponseDto getErrorResponse();
}
