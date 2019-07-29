/**
 * 
 */
package io.crs.mws.server.controller;

import static io.crs.mws.shared.api.ApiParameters.IID_TOKEN;
import static io.crs.mws.shared.api.ApiParameters.USER_AGENT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.FCM;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.SUBSCRIBE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.crs.mws.server.model.Subscription;
import io.crs.mws.server.service.AccountService;

/**
 * @author robi
 *
 */
@RestController
@RequestMapping(value = ROOT + FCM, produces = MediaType.APPLICATION_JSON_VALUE)
public class FcmController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(FcmController.class);

	static List<Subscription> subscriptions = new ArrayList<Subscription>();

	private final AccountService accountService;

	@Autowired
	FcmController(AccountService accountService) {
		logger.info("FcmController()");
		this.accountService = accountService;
	}

	@RequestMapping(value = SUBSCRIBE, params = { IID_TOKEN, USER_AGENT }, method = POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void subscribe(@RequestParam String webSafeKey, @RequestParam String iidToken, @RequestParam String userAgent) {
		logger.info("subscribe(IID_TOKEN=" + iidToken + ", USER_AGENT=" + userAgent + ")");
		try {
			accountService.fcmSubscribe(webSafeKey, iidToken, userAgent);
		} catch (Throwable e) {
			logger.info("subscribe->catch Exeption->" + e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping(value = SUBSCRIBE, method = DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void unsubscribeAll() {
		logger.info("unsubscribeAll()");
		subscriptions.clear();
	}
}
