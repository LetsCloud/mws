/**
 * 
 */
package io.crs.mws.client.core.security;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.GatekeeperWithParams;

/**
 * @author CR
 *
 */
public class HasPermissionsGatekeeper implements GatekeeperWithParams {
	private static final Logger LOGGER = Logger.getLogger(HasPermissionsGatekeeper.class.getName());

	private final CurrentUser currentUser;
	private String[] requiredPermissions;

	@Inject
	public HasPermissionsGatekeeper(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	public GatekeeperWithParams withParams(String[] params) {
		requiredPermissions = params;
		return this;
	}

	@Override
	public boolean canReveal() {
		return true;
	}
}