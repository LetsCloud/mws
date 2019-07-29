/**
 * 
 */
package io.crs.mws.server.controller;

import org.modelmapper.ModelMapper;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.dto.AccountDto;
import io.crs.mws.shared.exception.BaseException;
import io.crs.mws.shared.exception.RestApiException;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;

import static io.crs.mws.shared.api.ApiPaths.APIv1.CURRENTUSER;
import static io.crs.mws.shared.api.ApiPaths.APIv1.IS_LOGGED_IN;
import static io.crs.mws.shared.api.ApiPaths.APIv1.LOGIN;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.AdminV1.ADMIN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author robi
 *
 */
@RestController
@RequestMapping(value = ADMIN + ROOT + LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminAuthController extends BaseController {
//	private static final Logger logger = LoggerFactory.getLogger(AdminAuthController.class);

	private final AccountService accountService;

	private final ModelMapper modelMapper;

	@Autowired
	AdminAuthController(AccountService accountService, ModelMapper modelMapper) {
//		logger.info("AdminAuthController()");
		this.accountService = accountService;
		this.modelMapper = modelMapper;
	}

	@RequestMapping(method = GET, value = IS_LOGGED_IN)
	ResponseEntity<Boolean> isCurrentUserLoggedIn() {
		return new ResponseEntity<Boolean>(accountService.isCurrentUserLoggedIn(), OK);
	}

	@RequestMapping(method = GET, value = CURRENTUSER)
	ResponseEntity<AccountDto> getCurrentUser() throws RestApiException {
		Account account = accountService.getCurrentAccount();
		if (account == null)
			throw new RestApiException(new BaseException(ErrorTitleCode.LOGIN_USERNAME_NOT_FOUND + " account==null"));

		AccountDto appUserDto = modelMapper.map(account, AccountDto.class);
		return new ResponseEntity<AccountDto>(appUserDto, HttpStatus.OK);
	}
}
