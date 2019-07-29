/**
 * 
 */
package io.crs.mws.server.entity;

import java.util.ArrayList;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import io.crs.mws.server.model.RegistrationForm;

/**
 * @author robi
 *
 */
@Entity
public class Account extends BaseEntity {
//	private static final Logger logger = LoggerFactory.getLogger(AppUser.class.getName());

	/**
	 * Név
	 */
	private String nickname;

	/**
	 * Email cím
	 */
	@Index
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
	@Index
	private Boolean enabled;

	/**
	 * Firebase Messaging Token
	 */
	private List<FcmToken> fcmTokens = new ArrayList<FcmToken>();

	/**
	 * Verifikációs tóken
	 */
	private List<VerificationToken> verificationTokens;

	/**
	 * Paraméter nélküli kontruktor Objectify-hoz
	 */
	public Account() {
//		logger.info("AppUser()");
		this.enabled = false;
	}

	/**
	 * Konstruktor Registration-ból
	 * 
	 * @param registration
	 */
	public Account(RegistrationForm registration) {
		this();
		setNickname(registration.getNickname());
		setPassword(registration.getPassword());
		setEmail(registration.getEmail());
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String hashedPassword) {
		this.password = hashedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<VerificationToken> getVerificationTokens() {
		return verificationTokens;
	}

	public void setVerificationTokens(List<VerificationToken> verificationTokens) {
//		logger.info("setVerificationTokens()");
		this.verificationTokens = verificationTokens;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<FcmToken> getFcmTokens() {
		return fcmTokens;
	}

	public void setFcmTokens(List<FcmToken> fcmTokens) {
		this.fcmTokens = fcmTokens;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [nickname=" + nickname + ", email=" + email + ", imageUrl=" + imageUrl + ", enabled=" + enabled
				+ "]";
	}

}