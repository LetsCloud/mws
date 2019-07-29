/**
 * 
 */
package io.crs.mws.shared.exception;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class RestApiException extends Exception {
	
	public RestApiException(Throwable exception) {
		super(exception);
	}
	
	public BaseException getBaseException() {
		return (BaseException) getCause();
	}
}
