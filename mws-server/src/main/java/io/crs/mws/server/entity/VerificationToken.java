/**
 * 
 */
package io.crs.mws.server.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Index;

/**
 * @author robi
 *
 */
public class VerificationToken {
	private static final int EXPIRATION = 60 * 24;

	/**
	 * Az ÁFA kulcsok érvényeségi dátum szerint rendezését szolgáló Comparator
	 */
	public static Comparator<VerificationToken> ORDER_BY_TOKEN = new Comparator<VerificationToken>() {
		public int compare(VerificationToken one, VerificationToken other) {
			return one.getToken().compareTo(other.getToken());
		}
	};

	/**
	 * 
	 * @param tokens
	 * @param token
	 * @return
	 */
	public static VerificationToken getValidVatRate(List<VerificationToken> tokens, final String token) {
		return tokens.stream().filter(t -> t.getToken().equals(token)).findFirst().orElse(null);
	}

	@Index
	private String token;

	private Date expiryDate;

	private Boolean verified;

	public VerificationToken() {
	}

	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
		this.verified = false;
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

}
