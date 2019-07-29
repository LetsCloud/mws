/**
 * 
 */
package io.crs.mws.client.core.service;

import static io.crs.mws.shared.api.ApiParameters.IID_TOKEN;
import static io.crs.mws.shared.api.ApiParameters.USER_AGENT;
import static io.crs.mws.shared.api.ApiParameters.WEBSAFEKEY;
import static io.crs.mws.shared.api.ApiPaths.APIv1.FCM;
import static io.crs.mws.shared.api.ApiPaths.APIv1.ROOT;
import static io.crs.mws.shared.api.ApiPaths.APIv1.SUBSCRIBE;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

/**
 * @author CR
 *
 */
@Path(ROOT + FCM)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface FcmService extends RestService {

	@POST
	@Path(SUBSCRIBE)
	public void subscribe(@QueryParam(WEBSAFEKEY) String webSafeKey, @QueryParam(IID_TOKEN) String iidToken, @QueryParam(USER_AGENT) String userAgent,
			MethodCallback<Void> callback);
}
