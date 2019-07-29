/**
 * 
 */
package io.crs.mws.client.core.util;

// import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author CR
 *
 */
public class CryptUtils {

	public static String hashPassword(String username, String password) {
		return username;
//		return DigestUtils.sha256Hex(password + "{" + username + "}");
	}

}
