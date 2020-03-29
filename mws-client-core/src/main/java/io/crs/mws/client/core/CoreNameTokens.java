/**
 * 
 */
package io.crs.mws.client.core;

import io.crs.mws.shared.cnst.Constants;

/**
 * @author CR
 *
 */
public class CoreNameTokens {
	public static final String LOGIN = "/login";
	public static final String OAUTH2REDIRECT = "/" + Constants.OAUTH2_REDIRECT;
	public static final String HOME = "/home";

	public static final String PLACE_TOGO = "placeToGo";

	public static String getLogin() {
		return LOGIN;
	}

	public static String getHome() {
		return HOME;
	}

}
