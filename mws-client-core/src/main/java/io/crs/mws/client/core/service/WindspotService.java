/**
 * 
 */
package io.crs.mws.client.core.service;

import static io.crs.mws.shared.api.ApiPaths.APIv1.WINDSPOT;

import java.util.List;

import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import io.crs.mws.shared.dto.WindspotDto;

/**
 * @author robi
 *
 */
@Path(ROOT + WINDSPOT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface WindspotService extends RestService {

	@POST
	public void createOrSave(WindspotDto dto, MethodCallback<WindspotDto> callback);

	@GET
	@Path("/{webSafeKey}")
	public void get(@PathParam("webSafeKey") String webSafeKey, MethodCallback<WindspotDto> callback);

	@GET
	public void getAll(MethodCallback<List<WindspotDto>> callback);

	@DELETE
	public void delete(String webSafeKey, MethodCallback<Void> callback);

}
