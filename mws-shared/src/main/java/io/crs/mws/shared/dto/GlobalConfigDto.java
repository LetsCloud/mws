/**
 * 
 */
package io.crs.mws.shared.dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class GlobalConfigDto extends BaseDto {

	/**
	 * Key
	 */
	private String code;

	/**
	 * Key
	 */
	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		String ret = "GlobalConfigDto:{code=" + code + ", value=" + value + super.toString() + "}";
		return ret;
	}

}
