/**
 * 
 */
package io.crs.mws.server.entity;

import io.crs.mws.shared.exception.cnst.ErrorMessageCode;

/**
 * @author robi
 *
 */
public class UniqueKey {
	private String property;
	private Object value;
	private ErrorMessageCode exception;

	public UniqueKey(String property, Object value, ErrorMessageCode exception) {
		this.property = property;
		this.value = value;
		this.exception = exception;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ErrorMessageCode getException() {
		return exception;
	}

	public void setException(ErrorMessageCode exception) {
		this.exception = exception;
	}

}
