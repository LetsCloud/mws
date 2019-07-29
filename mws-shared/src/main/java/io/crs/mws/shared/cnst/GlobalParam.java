/**
 * 
 */
package io.crs.mws.shared.cnst;

import java.io.Serializable;

/**
 * @author robi
 *
 */
public enum GlobalParam implements Serializable {

	/**
	 * 
	 */
	FB1_API_KEY("Firebase - API Key"),

	/**
	 * 
	 */
	FB2_AUTH_DOMAIN("Firebase - Auth Domain"),

	/**
	 * 
	 */
	FB3_DATABASE_URL("Firebase - Database Url"),

	/**
	 * 
	 */
	FB4_PROJECT_ID("Firebase - Project Id"),

	/**
	 * 
	 */
	FB5_STORAGE_BUCKET("Firebase - Storage Bucket"),

	/**
	 * 
	 */
	FB6_MESSAGE_SENDER_ID("Firebase - Message Sender Id");

	private String paramName = null;

	private GlobalParam(String paramName) {
		this.paramName = paramName;
	}

	public String getParamName() {
		return paramName;
	};

}
