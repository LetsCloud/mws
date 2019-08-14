/**
 * 
 */
package io.crs.mws.server.controller;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.repository.AccountRepository;
import io.crs.mws.server.security2.TokenProvider;
import io.crs.mws.server.security2.UserPrincipal;
import io.crs.mws.server.security2.oauth2.exception.ResourceNotFoundException;
import io.crs.mws.server.security2.oauth2.user.CurrentUser;
import io.crs.mws.shared.cnst.SignupError;
import io.crs.mws.shared.dto.AccountDto;
import io.crs.mws.shared.dto.auth.SignupResponse;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author robi
 *
 */
@RestController
@RequestMapping(value = ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private static final String SITE_SECRET = "6Lc23bIUAAAAAF2CKG4WpUxzOfm3ZdLkEqwGIF7T";
	private static final String SECRET_PARAM = "secret";
	private static final String RESPONSE_PARAM = "response";
	private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

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
	public ResponseEntity<SignupResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
		logger.info("AuthController().registerUser()");
		JSONObject jsonObject;
		try {
			jsonObject = performRecaptchaSiteVerify(signUpRequest.getReCaptchaResponse());
			if (!jsonObject.getBoolean("success")) {
				return new ResponseEntity<SignupResponse>(new SignupResponse(false, SignupError.invalidRecaptha),
						HttpStatus.OK);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return new ResponseEntity<SignupResponse>(new SignupResponse(false, SignupError.failedRecaptcha),
					HttpStatus.OK);
		}

		Account account = accountRepo.findByEmail(signUpRequest.getEmail());
		if (account != null) {
			return new ResponseEntity<SignupResponse>(new SignupResponse(false, SignupError.existingEmail),
					HttpStatus.OK);
		}

		// Creating user's account
		account = new Account();
		account.setNickname(signUpRequest.getNickname());
		account.setEmail(signUpRequest.getEmail());
		account.setPassword(signUpRequest.getPassword());

		account.setPassword(passwordEncoder.encode(account.getPassword()));

		try {
			accountRepo.save(account);
			return new ResponseEntity<SignupResponse>(new SignupResponse(true, SignupError.successful), HttpStatus.OK);
		} catch (EntityValidationException e) {
			e.printStackTrace();
			return new ResponseEntity<SignupResponse>(new SignupResponse(false, SignupError.invalidEntity),
					HttpStatus.OK);
		} catch (UniqueIndexConflictException e) {
			e.printStackTrace();
			return new ResponseEntity<SignupResponse>(new SignupResponse(false, SignupError.indexColossion),
					HttpStatus.OK);
		}
	}

	private JSONObject performRecaptchaSiteVerify(String recaptchaResponseToken) throws IOException {
		URL url = new URL(SITE_VERIFY_URL);
		StringBuilder postData = new StringBuilder();
		addParam(postData, SECRET_PARAM, SITE_SECRET);
		addParam(postData, RESPONSE_PARAM, recaptchaResponseToken);

		return postAndParseJSON(url, postData.toString());
	}

	private JSONObject postAndParseJSON(URL url, String postData) throws IOException {
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		urlConnection.setRequestProperty("charset", StandardCharsets.UTF_8.displayName());
		urlConnection.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		urlConnection.setUseCaches(false);
		urlConnection.getOutputStream().write(postData.getBytes(StandardCharsets.UTF_8));
		JSONTokener jsonTokener = new JSONTokener(urlConnection.getInputStream());
		return new JSONObject(jsonTokener);
	}

	private StringBuilder addParam(StringBuilder postData, String param, String value)
			throws UnsupportedEncodingException {
		if (postData.length() != 0) {
			postData.append("&");
		}
		return postData.append(String.format("%s=%s", URLEncoder.encode(param, StandardCharsets.UTF_8.displayName()),
				URLEncoder.encode(value, StandardCharsets.UTF_8.displayName())));
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
//	@PreAuthorize("hasRole('USER')")
	public @ResponseBody ResponseEntity<AccountDto> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		Account a = accountRepo.findByEmail(userPrincipal.getEmail());
		if (a == null)
			throw new ResourceNotFoundException("User", "id", userPrincipal.getEmail());
		return new ResponseEntity<AccountDto>(modelMapper.map(a, AccountDto.class), OK);
	}
}