/**
 * 
 */
package io.crs.mws.client.app.auth.login;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
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
	MaterialAnchorButton googleLogin;

	@Inject
	LoginView(Binder uiBinder, Driver driver) {
		logger.info("LoginView()");
		initWidget(uiBinder.createAndBindUi(this));
		this.driver = driver;
		driver.initialize(this);
	}

	@Override
	public void setPlaceToGo(String placeTogo, LoginRequest loginRequest) {
		googleLogin.setHref(UrlUtils.getBaseUrl() + "/oauth2/authorize/google?redirect_uri=" + UrlUtils.getBaseUrl()
				+ "/app/start.html#oauth2redirect");
		/*
		 * if (!Strings.isNullOrEmpty(placeTogo)) {
		 * googleLogin.setHref(googleLogin.getHref() + "?" +
		 * LoggedInGatekeeper.PLACE_TO_GO + "=" + placeTogo); }
		 */
		logger.info("LoginView().setPlaceToGo->googleLogin.getHref()=" + googleLogin.getHref());

		driver.edit(loginRequest);

		Timer t = new Timer() {
			@Override
			public void run() {
				logger.info("LoginView().setPlaceToGo->run()");
				email.setFocus(true);
				logger.info("LoginView().setPlaceToGo->run()-2");
			}
		};
		t.schedule(100);
	}

	@UiHandler("submit")
	void onSubmitClicked(ClickEvent event) {
		LoginRequest dto = driver.flush();
		getUiHandlers().login(dto);
	}

	@Override
	public void setAppCode(String appCode) {
		brandPanel.add(new HTML("Windspot <span>Navigator v1.0</span>"));
	}

	@Override
	public void displayError(EntityPropertyCode code, String message) {
		MaterialToast.fireToast(message);
	}
}