/**
 * 
 */
package io.crs.mws.server.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.repository.AccountRepository;
import io.crs.mws.server.security2.TokenProvider;
import io.crs.mws.server.security2.UserPrincipal;
import io.crs.mws.server.security2.oauth2.exception.BadRequestException;
import io.crs.mws.server.security2.oauth2.exception.ResourceNotFoundException;
import io.crs.mws.server.security2.oauth2.user.CurrentUser;
import io.crs.mws.shared.dto.AccountDto;
import io.crs.mws.shared.dto.auth.ApiResponse;
import io.crs.mws.shared.dto.auth.AuthResponse;
import io.crs.mws.shared.dto.auth.LoginRequest;
import io.crs.mws.shared.dto.auth.SignUpRequest;
import io.crs.mws.shared.exception.EntityValidationException;
import io.crs.mws.shared.exception.UniqueIndexConflictException;

import javax.validation.Valid;

import static io.crs.mws.shared.api.ApiPaths.APIv1.AUTH;
import static io.crs.mws.shared.api.ApiPaths.APIv1.CURRENTUSER;
import static io.crs.mws.shared.api.ApiPaths.APIv1.LOGIN;
import static io.crs.mws.shared.api.ApiPaths.APIv1.PUBLIC;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.SIGNUP;
import static org.springframework.http.HttpStatus.OK;

import java.net.URI;

/**
 * @author robi
 *
 */
@RestController
@RequestMapping(value = ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	AuthController() {
		logger.info("AuthController()");
	}

	@PostMapping(PUBLIC + SIGNUP)
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		Account a = accountRepo.findByEmail(signUpRequest.getEmail());
		if (a != null) {
			throw new BadRequestException("Email address already in use.");
		}

		// Creating user's account
		Account account = new Account();
		account.setNickname(signUpRequest.getNickname());
		account.setEmail(signUpRequest.getEmail());
		account.setPassword(signUpRequest.getPassword());

		account.setPassword(passwordEncoder.encode(account.getPassword()));

		Account result;
		try {
			result = accountRepo.save(account);

			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
					.buildAndExpand(result.getId()).toUri();

			return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully@"));
		} catch (EntityValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UniqueIndexConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me").build().toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Email address already in use."));
	}

	@PostMapping(PUBLIC + LOGIN)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		logger.info("authenticateUser()");

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@GetMapping(AUTH + CURRENTUSER)
	@PreAuthorize("hasRole('USER')")
	public @ResponseBody ResponseEntity<AccountDto> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		Account a = accountRepo.findByEmail(userPrincipal.getEmail());
		if (a == null)
			throw new ResourceNotFoundException("User", "id", userPrincipal.getEmail());
		return new ResponseEntity<AccountDto>(modelMapper.map(a, AccountDto.class), OK);
	}
}