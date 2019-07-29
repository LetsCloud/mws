/**
 * 
 */
package io.crs.mws.server.login;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.security.AccountDetails;
import io.crs.mws.server.security2.gae.GaeUser;
import io.crs.mws.shared.dto.AccountDto;

/**
 * A Spring Security context-be bejelentkezett felhasználó lekérdezését szolgáló
 * osztály
 * 
 * @author robi
 *
 */
@Component
public class LoggedInChecker {
//	private static final Logger logger = LoggerFactory.getLogger(LoggedInChecker.class);

	@Autowired
	private ModelMapper modelMapper;

	public LoggedInChecker() {
//		logger.info("LoggedInChecker()");
	}

	/**
	 * Visszaadja a bejelentkezett felhasználó adatait AppUser entitás formájában
	 * 
	 * @return
	 */
	public Account getLoggedInUser() {
		Account account = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();

			// principal can be "anonymousUser" (String)
			if (principal instanceof AccountDetails) {
				AccountDetails userDetails = (AccountDetails) principal;
				AccountDto dto = userDetails.getAccount();
				try {
					account = modelMapper.map(dto, Account.class);
					return account;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

			// GaeUser esetén
			if (principal instanceof GaeUser) {
				GaeUser gaeUser = (GaeUser) principal;
				account = new Account();
				account.setEmail(gaeUser.getEmail());
				account.setNickname(gaeUser.getNickname());
				return account;
			}
		}
		return account;
	}
}
