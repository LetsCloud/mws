/**
 * 
 */
package io.crs.mws.client.core.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import io.crs.mws.shared.dto.AccountDto;
import io.crs.mws.shared.dto.auth.SignupResponse;
import io.crs.mws.shared.dto.auth.AuthResponse;
import io.crs.mws.shared.dto.auth.LoginRequest;
import io.crs.mws.shared.dto.auth.SignUpRequest;

import static io.crs.mws.shared.api.ApiPaths.APIv1.AUTH;
import static io.crs.mws.shared.api.ApiPaths.APIv1.CURRENTUSER;
import static io.crs.mws.shared.api.ApiPaths.APIv1.LOGIN;
import static io.crs.mws.shared.api.ApiPaths.APIv1.LOGOUT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.PUBLIC;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.SIGNUP;

/**
 * @author CR
 *
 */
@Path(ROOT)
@Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
public interface AuthService extends RestService {

	@POST
	@Path(PUBLIC + SIGNUP)
	public void signUp(SignUpRequest signUpRequest, MethodCallback<SignupResponse> callback);

	@POST
	@Path(PUBLIC + LOGIN)
	public void login(LoginRequest loginRequest, MethodCallback<AuthResponse> callback);

	@GET
	@Path(AUTH + CURRENTUSER)
	public void getCurrentUser(MethodCallback<AccountDto> callback);

	@POST
	@Path(AUTH + LOGOUT)
	public void logout(MethodCallback<Void> callback);
}
