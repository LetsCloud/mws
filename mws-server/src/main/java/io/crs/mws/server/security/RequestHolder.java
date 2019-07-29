/**
 * 
 */
package io.crs.mws.server.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author robi
 *
 */
public class RequestHolder {

	private @Autowired HttpServletRequest request;

	public String getServerName() {
		if (request == null) return "null request";
		return request.getServerName();
	}
}
