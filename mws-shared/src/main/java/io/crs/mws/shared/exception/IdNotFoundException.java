/**
 * 
 */
package io.crs.mws.shared.exception;

/**
 * @author CR
 *
 */
@SuppressWarnings("serial")
public class IdNotFoundException extends BaseException {

	private String entiy;

	private Object value;

	public IdNotFoundException(String entiy, Object value) {
		super();
		this.entiy = entiy;
		this.value = value;
	}

	public String getEntiy() {
		return entiy;
	}

	public Object getValue() {
		return value;
	}
}
