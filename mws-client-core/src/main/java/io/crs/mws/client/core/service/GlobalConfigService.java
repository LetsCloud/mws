/**
 * 
 */
package io.crs.mws.client.core.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import io.crs.mws.shared.dto.GlobalConfigDto;

import static io.crs.mws.shared.api.ApiParameters.WEBSAFEKEY;
import static io.crs.mws.shared.api.ApiPaths.PATH_WEBSAFEKEY;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ADMIN;
import static io.crs.mws.shared.api.ApiPaths.APIv1.GLOBAL_CONFIG;

/**
 * @author robi
 *
 */
@Path(ROOT)
@Produces(MediaType.APPLICATION_JSON)
public interface GlobalConfigService extends RestService {

	@GET
	@Path(GLOBAL_CONFIG)
	public void getAll(MethodCallback<List<GlobalConfigDto>> callback);

	@GET
	@Path(GLOBAL_CONFIG + PATH_WEBSAFEKEY)
	public void get(@PathParam(WEBSAFEKEY) String webSafeKey, MethodCallback<GlobalConfigDto> callback);

	@POST
	@Path(ADMIN + GLOBAL_CONFIG)
	public void saveOrCreate(GlobalConfigDto dto, MethodCallback<GlobalConfigDto> callback);

	@DELETE
	@Path(ADMIN + PATH_WEBSAFEKEY)
	public void delete(@PathParam(WEBSAFEKEY) String webSafeKey, MethodCallback<Void> callback);

}
