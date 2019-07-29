/**
 * 
 */
package io.crs.mws.client.app.auth.signup;

import com.gwtplatform.mvp.client.UiHandlers;

import io.crs.mws.shared.dto.auth.SignUpRequest;

/**
 * @author robi
 *
 */
public interface SignupUiHandlers extends UiHandlers {

	void signUp(SignUpRequest signUpRequest);

}
