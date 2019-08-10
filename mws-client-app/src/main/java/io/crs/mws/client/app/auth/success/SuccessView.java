/**
 * 
 */
package io.crs.mws.client.app.auth.success;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

import io.crs.mws.client.core.resources.ThemeParams;

/**
 * @author CR
 *
 */
public class SuccessView extends ViewWithUiHandlers<SuccessUiHandlers> implements SuccessPresenter.MyView {
	private static Logger logger = Logger.getLogger(SuccessView.class.getName());

	interface Binder extends UiBinder<Widget, SuccessView> {
	}

	@UiField
	MaterialRow container;

	@UiField
	MaterialPanel headerPanel;

	@UiField
	HTMLPanel brandPanel;

	@Inject
	SuccessView(Binder uiBinder, ThemeParams themeParams) {
		logger.info("SuccessView()");
		initWidget(uiBinder.createAndBindUi(this));
		headerPanel.getElement().getStyle().setBackgroundColor(themeParams.getPrimaryColor());
		container.getElement().getStyle().setBackgroundColor(themeParams.getPrimaryLightColor());
	}
}