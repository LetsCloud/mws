/**
 * 
 */
package io.crs.mws.client.core.util;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.dispatcher.DefaultFilterawareDispatcher;
import org.fusesource.restygwt.client.dispatcher.DispatcherFilter;

import com.google.common.base.Strings;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Cookies;

/**
 * @author robi
 *
 */
public class OauthUtils {
	public static final String ACCESS_TOKEN = "accessToken";

	public static String loadAccessToken() {
		String token = Cookies.getCookie(ACCESS_TOKEN);
		setHeaderToken(token);
		return(token);
	}

	public static void storeAccessToken(String token) {
		Cookies.setCookie(ACCESS_TOKEN, token);
		setHeaderToken(token);
	}

	public static void removeAccessToken() {
		Cookies.removeCookie(ACCESS_TOKEN);
		setHeaderToken("");
	}

	private static void setHeaderToken(String token) {
		if (!Strings.isNullOrEmpty(token)) {
			Defaults.setDispatcher(new DefaultFilterawareDispatcher(new DispatcherFilter() {

				@Override
				public boolean filter(Method method, RequestBuilder builder) {
					builder.setHeader("Authorization", "Bearer " + token);
					return true;
				}
			}));
		}
	}
}
