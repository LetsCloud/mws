/**
 * 
 */
package io.crs.mws.client.core.service;

import static io.crs.mws.shared.api.ApiPaths.APIv1.ACCOUNT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.RestService;

import io.crs.mws.shared.dto.AccountDto;

/**
 * @author robi
 *
 */
@Path(ROOT + ACCOUNT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AccountService extends RestService {

	@POST
	public AccountDto createOrSave(AccountDto dto);

	@GET
	public AccountDto get(String webSafeKey);

	@DELETE
	public void delete(String webSafeKey);

}
