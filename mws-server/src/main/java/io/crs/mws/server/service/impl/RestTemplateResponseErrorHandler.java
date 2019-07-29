/**
 * 
 */
package io.crs.mws.server.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.googlecode.objectify.NotFoundException;

/**
 * @author robi
 *
 */
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

		logger.info("handleError()->getStatusText=" + httpResponse.getStatusText());
		logger.info("handleError()->getBody=" + httpResponse.getBody());

		return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
				|| httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {

		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			// handle SERVER_ERROR
			logger.info("handleError()->getStatusText1=" + httpResponse.getStatusText());
			logger.info("handleError()->getBody1=" + httpResponse.getBody());
		} else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
			// handle CLIENT_ERROR
			logger.info("handleError()->getStatusText2=" + httpResponse.getStatusText());
			logger.info("handleError()->getBody2=" + httpResponse.getBody());
			if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new NotFoundException();
			}
		}
	}
}