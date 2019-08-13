/**
 * 
 */
package io.crs.mws.client.app.auth.signup;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.api.ApiRegistry;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.incubator.client.google.recaptcha.ReCaptcha;
import gwt.material.design.incubator.client.google.recaptcha.api.RecaptchaApi;
import gwt.material.design.incubator.client.google.recaptcha3.ReCaptcha3;
import gwt.material.design.incubator.client.google.recaptcha3.api.RecaptchaApi3;
import io.crs.mws.client.core.resources.ThemeParams;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.shared.dto.auth.SignUpRequest;

/**
 * @author robi
 *
 */
public class SignupView extends ViewWithUiHandlers<SignupUiHandlers>
		implements SignupPresenter.MyView, Editor<SignUpRequest> {
	private static Logger logger = Logger.getLogger(SignupView.class.getName());

	interface Binder extends UiBinder<Widget, SignupView> {
	}

	interface Driver extends SimpleBeanEditorDriver<SignUpRequest, SignupView> {
	}

	private final Driver driver;

	@UiField
	MaterialRow container;

	@UiField
	MaterialPanel headerPanel, capthcaPanel;

	@UiField
	HTMLPanel brandPanel;

	@UiField
	MaterialTextBox nickname, email, password;

	@Ignore
	@UiField
	MaterialTextBox confirmpsw;

	private ReCaptcha recaptcha;

	@Inject
	SignupView(Binder uiBinder, Driver driver, ThemeParams themeParams, AppData appData) {
		logger.info("SignupView()");
		initWidget(uiBinder.createAndBindUi(this));

		this.driver = driver;
		driver.initialize(this);

		headerPanel.getElement().getStyle().setBackgroundColor(themeParams.getPrimaryColor());
		container.getElement().getStyle().setBackgroundColor(themeParams.getPrimaryLightColor());

		brandPanel.add(new HTML(appData.getName()));

		recaptcha = new ReCaptcha();
		recaptcha.setDisplay(Display.INLINE_BLOCK);
		capthcaPanel.add(recaptcha);
	}

	@Override
	public void start(SignUpRequest signUpRequest) {
		driver.edit(signUpRequest);

		Scheduler.get().scheduleDeferred(() -> nickname.setFocus(true));

		/*
		 * RecaptchaApi3 recaptchaApi3 = new
		 * RecaptchaApi3("6LfWqbIUAAAAAAur8ek61dIBOrBfZweNjjawzO0b");
		 * recaptcha.load(recaptchaApi3, token -> MaterialToast.fireToast("Token=" +
		 * token));
		 */

		RecaptchaApi recaptchaApi = new RecaptchaApi("6Lc23bIUAAAAAF56BnN9tdgwJGBZ07UdWvTu83KH");
		ApiRegistry.register(recaptchaApi, new Callback<Void, Exception>() {
			@Override
			public void onFailure(Exception reason) {
				MaterialToast.fireToast(reason.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				recaptcha.load(recaptchaApi);
			}
		});
	}

	@UiHandler("submit")
	void onSubmitClicked(ClickEvent event) {
		SignUpRequest dto = driver.flush();
		dto.setReCaptchaResponse(recaptcha.getResponse());
		logger.info("SignupView().onSubmitClicked()->reCaptchaResponse=" + dto.getReCaptchaResponse());
		getUiHandlers().signUp(dto);
	}
}