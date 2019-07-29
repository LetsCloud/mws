/**
 * 
 */
package io.crs.mws.shared.dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class ComboDto extends BaseDto {

	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
