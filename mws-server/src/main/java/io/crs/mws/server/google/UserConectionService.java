/**
 * 
 */
package io.crs.mws.server.google;

import java.util.List;
import java.util.Set;

import io.crs.mws.server.entity.SocialConnection;

/**
 * @author robi
 *
 */
public interface UserConectionService {

	List<String> getLocalIdsByConnection(String providerId, String providerUsrId);

	Set<String> getLocalIdsByProviderUsrIds(String providerId, Set<String> providerUsrIds);

	List<SocialConnection> getConnectionDataByLocalId(String localId);

	List<SocialConnection> getConnectionDataByLocalIdAndProviderId(String localId, String providerId);
	
	void addConnection(String localId, SocialConnection socialConnection);
	
	void updateConnection(String localId, SocialConnection socialConnection);

	void removeConnections(String localId, String providerId);
	
	void removeConnection(String localId, String providerId, String providerUserId);
}
