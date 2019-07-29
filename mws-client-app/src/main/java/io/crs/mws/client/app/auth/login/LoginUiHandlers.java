/**
 * 
 */
package io.crs.mws.client.app.auth.login;

import com.gwtplatform.mvp.client.UiHandlers;

import io.crs.mws.shared.dto.auth.LoginRequest;

/**
 * @author robi
 *
 */

public interface LoginUiHandlers extends UiHandlers {
	void login(LoginRequest loginRequest);
}