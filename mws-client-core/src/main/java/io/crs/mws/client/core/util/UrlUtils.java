/**
 * 
 */
package io.crs.mws.client.core.util;

import com.google.gwt.core.client.GWT;

/**
 * @author robi
 *
 */
public class UrlUtils {

	public static String getBaseUrl() {
		String baseUrl = GWT.getHostPageBaseURL();
		if (baseUrl.contains("admin")) {
			if (baseUrl.endsWith("/"))
				return baseUrl.substring(0, baseUrl.length() - 1);
		}
		int shortening = (baseUrl.endsWith("/")) ? 5 : 4;
		return baseUrl.substring(0, baseUrl.length() - shortening);
	}

	public static String getImageUrl() {
		return getBaseUrl() + "/image/";
	}
}
