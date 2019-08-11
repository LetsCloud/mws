/**
 * 
 */
package io.crs.mws.client.adm.login;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;

import io.crs.mws.client.core.security.login.LoginPresenter;
import io.crs.mws.client.core.security.login.LoginUiHandlers;
import io.crs.mws.client.core.util.UrlUtils;
import io.crs.mws.shared.dto.EntityPropertyCode;
import io.crs.mws.shared.dto.auth.LoginRequest;

/**
 * @author CR
 *
 */
public class LoginView extends ViewWithUiHandlers<LoginUiHandlers>
		implements LoginPresenter.MyView {
	private static Logger logger = Logger.getLogger(LoginView.class.getName());

	interface Binder extends UiBinder<Widget, LoginView> {
	}

	@UiField
	MaterialPanel headerPanel;

	@UiField
	HTMLPanel brandPanel;

	@UiField
	MaterialAnchorButton googleLogin;

	@Inject
	LoginView(Binder uiBinder) {
		logger.info("LoginView()");
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPlaceToGo(String placeTogo, LoginRequest loginRequest) {
		googleLogin.setHref(UrlUtils.getBaseUrl() + "/oauth2/authorize/google?redirect_uri=" + UrlUtils.getBaseUrl()
				+ "/adm/start.html#oauth2redirect");
	}

	@Override
	public void displayError(EntityPropertyCode code, String message) {
		MaterialToast.fireToast(message);
	}
}