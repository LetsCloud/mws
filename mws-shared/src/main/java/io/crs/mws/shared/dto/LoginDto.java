/**
 * 
 */
package io.crs.mws.shared.dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class LoginDto implements Dto {
	private String username;
	private String password;
	private Boolean remeberMe;

	public LoginDto(String username, String password, Boolean remeberMe) {
		this.username = username;
		this.password = password;
		this.remeberMe = remeberMe;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getRemeberMe() {
		return remeberMe;
	}

	public void setRemeberMe(Boolean remeberMe) {
		this.remeberMe = remeberMe;
	}

}
