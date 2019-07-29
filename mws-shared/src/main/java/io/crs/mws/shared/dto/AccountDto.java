/**
 * 
 */
package io.crs.mws.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class AccountDto extends BaseDto {
	/**
	 * Név
	 */
	private String nickname;

	/**
	 * Email cím
	 */
	private String email;

	/**
	 * Hash-elt jelszó
	 */
	private String password;

	/**
	 * Fotó
	 */
	private String imageUrl;

	/**
	 * Engedélyezett
	 */
	private Boolean enabled;

	/**
	 * Firebase Messaging Token
	 */
	private List<FcmTokenDto> fcmTokens = new ArrayList<FcmTokenDto>();

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<FcmTokenDto> getFcmTokens() {
		return fcmTokens;
	}

	public void setFcmTokens(List<FcmTokenDto> fcmTokens) {
		this.fcmTokens = fcmTokens;
	}

	@Override
	public String toString() {
		return "AccountDto [nickname=" + nickname + ", email=" + email + ", password=" + password + ", imageUrl="
				+ imageUrl + ", enabled=" + enabled + ", fcmTokens=" + fcmTokens + "]";
	}

}
