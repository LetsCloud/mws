/**
 * 
 */
package io.crs.mws.shared.dto;

import java.util.HashMap;
import java.util.Map;

import io.crs.mws.shared.exception.cnst.ErrorMessageCode;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;
import io.crs.mws.shared.exception.cnst.ValueCode;

/**
 * @author CR
 *
 */
@SuppressWarnings("serial")
public class ErrorResponseDto implements Dto {

	private int errorCode;

	private ErrorTitleCode titleCode;

	private ErrorMessageCode messageCode;

	private Map<ValueCode, Object> valuesMap = new HashMap<ValueCode, Object>();
	
	private String property;

	private String message;

	public ErrorResponseDto() {	
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorTitleCode getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(ErrorTitleCode titleCode) {
		this.titleCode = titleCode;
	}

	public ErrorMessageCode getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(ErrorMessageCode messageCode) {
		this.messageCode = messageCode;
	}

	public Map<ValueCode, Object> getValuesMap() {
		return valuesMap;
	}

	public void setValuesMap(Map<ValueCode, Object> valuesMap) {
		this.valuesMap = valuesMap;
	}

	public void addValue(ValueCode key, Object value) {
		this.valuesMap.put(key, value);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResponseDto [errorCode=" + errorCode + ", titleCode=" + titleCode + ", messageCode=" + messageCode
				+ ", valuesMap=" + valuesMap + ", property=" + property + ", message=" + message + "]";
	}
	
}
