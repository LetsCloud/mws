/**
 * 
 */
package io.crs.mws.server.repository.ofy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.ObjectifyService;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.entity.GlobalConfig;
import io.crs.mws.server.entity.Windspot;

/**
 * @author CR
 *
 */
public class ObjectifyRegistration {
	private static final Logger logger = LoggerFactory.getLogger(ObjectifyRegistration.class);

	static {
		logger.info("ObjectifyService.register");
		ObjectifyService.register(GlobalConfig.class);
		ObjectifyService.register(Account.class);
		ObjectifyService.register(Windspot.class);
	}

}
