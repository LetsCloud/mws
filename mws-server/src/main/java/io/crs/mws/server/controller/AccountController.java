/**
 * 
 */
package io.crs.mws.server.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.security.UserPrincipal;
import io.crs.mws.server.security.oauth2.user.CurrentUser;
import io.crs.mws.server.service.AccountService;
import io.crs.mws.shared.dto.AccountDto;
import io.crs.mws.shared.exception.RestApiException;

import static io.crs.mws.shared.api.ApiParameters.WEBSAFEKEY;
import static io.crs.mws.shared.api.ApiPaths.PATH_WEBSAFEKEY;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ACCOUNT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author robi
 *
 */
@RestController
@RequestMapping(value = ROOT + ACCOUNT, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController extends CrudController<Account, AccountDto> {

	@Autowired
	AccountController(AccountService service, ModelMapper modelMapper) {
		super(Account.class, service, modelMapper);
	}

	@Override
	protected AccountDto createDto(Account entity) {
		return modelMapper.map(entity, AccountDto.class);
	}

	@RequestMapping(value = PATH_WEBSAFEKEY, method = GET)
	public ResponseEntity<AccountDto> get(@CurrentUser UserPrincipal userPrincipal, @PathVariable String webSafeKey)
			throws RestApiException {
		return super.get(webSafeKey);
	}

	@RequestMapping(method = POST)
	public ResponseEntity<AccountDto> saveOrCreate(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody AccountDto dto) throws RestApiException {
		return super.saveOrCreate(dto);
	}

	@RequestMapping(value = PATH_WEBSAFEKEY, method = DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable(WEBSAFEKEY) String webSafeKey)
			throws RestApiException {
		super.delete(webSafeKey);
	}
}
