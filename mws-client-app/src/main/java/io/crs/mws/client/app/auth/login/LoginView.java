/**
 * 
 */
package io.crs.mws.client.app.auth.login;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

import io.crs.mws.client.core.CoreNameTokens;
import io.crs.mws.client.core.resources.ThemeParams;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.login.LoginPresenter;
import io.crs.mws.client.core.security.login.LoginUiHandlers;
import io.crs.mws.client.core.util.UrlUtils;
import io.crs.mws.shared.dto.EntityPropertyCode;
import io.crs.mws.shared.dto.auth.LoginRequest;

/**
 * @author robi
 *
 */
public class LoginView extends ViewWithUiHandlers<LoginUiHandlers>
		implements LoginPresenter.MyView, Editor<LoginRequest> {
	private static Logger logger = Logger.getLogger(LoginView.class.getName());

	interface Binder extends UiBinder<Widget, LoginView> {
	}

	interface Driver extends SimpleBeanEditorDriver<LoginRequest, LoginView> {
	}

	private final Driver driver;

	@Ignore
	@UiField
	MaterialRow container;

	@Ignore
	@UiField
	MaterialPanel headerPanel;

	@Ignore
	@UiField
	HTMLPanel brandPanel;

	@UiField
	MaterialTextBox email;

	@UiField
	MaterialTextBox password;

	@Ignore
	@UiField
	MaterialCheckBox rememberMe;

	@Ignore
	@UiField
	MaterialAnchorButton googleLogin, facebookLogin, twitteLogin, linkedInLogin;

	@Inject
	LoginView(Binder uiBinder, Driver driver, ThemeParams themeParams, AppData appData) {
		logger.info("LoginView()");
		initWidget(uiBinder.createAndBindUi(this));
		
		this.driver = driver;
		driver.initialize(this);
		
		headerPanel.getElement().getStyle().setBackgroundColor(themeParams.getPrimaryColor());
		container.getElement().getStyle().setBackgroundColor(themeParams.getPrimaryLightColor());
		brandPanel.add(new HTML(appData.getName()));
	}

	@Override
	public void setPlaceToGo(String placeTogo, LoginRequest loginRequest) {
		googleLogin.setHref(UrlUtils.getBaseUrl() + "/oauth2/authorize/google?redirect_uri=" + UrlUtils.getBaseUrl()
				+ "/app/start.html#" + CoreNameTokens.OAUTH2REDIRECT);
		driver.edit(loginRequest);

		Scheduler.get().scheduleDeferred(() -> email.setFocus(true));
	}

	@UiHandler("submit")
	void onSubmitClicked(ClickEvent event) {
		LoginRequest dto = driver.flush();
		getUiHandlers().login(dto);
	}

	@Override
	public void displayError(EntityPropertyCode code, String message) {
		MaterialToast.fireToast(message, "toastError");
	}
}