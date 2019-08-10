/**
 * 
 */
package io.crs.mws.client.adm.service;

import static io.crs.mws.shared.api.ApiPaths.APIv1.AUTH;
import static io.crs.mws.shared.api.ApiPaths.APIv1.CURRENTUSER;
import static io.crs.mws.shared.api.ApiPaths.APIv1.IS_LOGGED_IN;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.AdminV1.ADMIN;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.TextCallback;

import io.crs.mws.shared.dto.AccountDto;

/**
 * @author robi
 *
 */
@Path(ADMIN+ROOT+AUTH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthService extends RestService {

	@GET
	@Path(CURRENTUSER)
	public void getCurrentUser(MethodCallback<AccountDto> callback);

	@POST
	@Path(IS_LOGGED_IN)
	public void isLoggedIn(TextCallback callback);
}
