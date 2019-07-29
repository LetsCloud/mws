/**
 * 
 */
package io.crs.mws.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.crs.mws.server.service.FcmService;

/**
 * @author robi
 *
 */
public class FcmServiceImpl implements FcmService {
	private static final Logger logger = LoggerFactory.getLogger(FcmServiceImpl.class);

	private static final String HOST_URL = "https://fcm.googleapis.com/fcm/send";
//	private static final String HOST_URL = "https://fcm.googleapis.com/v1/projects/hw-cloud4/messages:send";
	private static final String HEAD_AUTH = "Authorization";
	private static final String SERVER_KEY = "key=AAAAF0SJFQs:APA91bHuujFobqGu2ynQ_hBA8jyNNOibKxITNAyEmr4NAjL1FOOmbxrv7TT2rL1kTsGQAaGI4nIDiEt1qaW9VOekRaaTJ84Q_QRakKyrwSngUljxJqqwWUlcClbddLKXKiX5JnEyQ1JT";
	// private static final String SERVER_KEY =
	// "AIzaSyCXBYYVwGlX49iDmE-DSXzJn4Mu6uYnEO0";
	private static final String HEAD_TYPE = "Content-Type";
	private static final String APP_JSON = "application/json";

	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;

	public FcmServiceImpl() {
		this.rest = new RestTemplate();
		this.headers = new HttpHeaders();
		
		rest.setErrorHandler(new RestTemplateResponseErrorHandler());
		headers.add(HEAD_TYPE, APP_JSON);
		headers.add(HEAD_AUTH, SERVER_KEY);
	}

	public String postMessage(String json) {
		logger.info("postMessage()->json=" + json);
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange(HOST_URL, HttpMethod.POST, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		logger.info("responseEntity.getBody()=" + responseEntity.getBody());
		return responseEntity.getBody();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
