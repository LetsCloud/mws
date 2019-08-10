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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.repository.AccountRepository;
import io.crs.mws.server.security2.UserPrincipal;
import io.crs.mws.server.security2.oauth2.exception.ResourceNotFoundException;
import io.crs.mws.server.security2.oauth2.user.CurrentUser;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.dto.AccountDto;
import io.crs.mws.shared.exception.BaseException;
import io.crs.mws.shared.exception.RestApiException;
import io.crs.mws.shared.exception.cnst.ErrorTitleCode;

import static io.crs.mws.shared.api.ApiPaths.APIv1.AUTH;
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
@Controller
@RequestMapping(value = ADMIN + ROOT + LOGIN)
public class AdminAuthController extends BaseController {
//	private static final Logger logger = LoggerFactory.getLogger(AdminAuthController.class);

	private final AccountRepository accountRepo;

	private final ModelMapper modelMapper;

	@Autowired
	AdminAuthController(AccountRepository accountRepo, ModelMapper modelMapper) {
//		logger.info("AdminAuthController()");
		this.accountRepo = accountRepo;
		this.modelMapper = modelMapper;
	}

	@GetMapping(AUTH + CURRENTUSER)
	@PreAuthorize("hasRole('USER')")
	public @ResponseBody ResponseEntity<AccountDto> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		Account a = accountRepo.findByEmail(userPrincipal.getEmail());
		if (a == null)
			throw new ResourceNotFoundException("User", "id", userPrincipal.getEmail());
		return new ResponseEntity<AccountDto>(modelMapper.map(a, AccountDto.class), OK);
	}

	@GetMapping(AUTH + IS_LOGGED_IN)
	public String getIsLoggedIn() {
		return "OK";
	}
}
