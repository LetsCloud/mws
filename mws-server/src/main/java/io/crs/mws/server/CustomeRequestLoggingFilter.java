/**
 * 
 */
package io.crs.mws.server;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @author robi
 *
 */
public class CustomeRequestLoggingFilter extends CommonsRequestLoggingFilter {

	public CustomeRequestLoggingFilter() {
		super.setIncludeQueryString(true);
		super.setIncludePayload(true);
		super.setMaxPayloadLength(10000);
	}
}
