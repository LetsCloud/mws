/**
 * 
 */
package io.crs.mws.client.core.security.oauth2redirect;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialLabel;

/**
 * @author robi
 *
 */
public class Oauth2RedirectView extends ViewWithUiHandlers<Oauth2RedirectUiHandlers>
		implements Oauth2RedirectPresenter.MyView {

	interface Binder extends UiBinder<Widget, Oauth2RedirectView> {
	}

	@UiField
	MaterialLabel email;
	
	@Inject
	Oauth2RedirectView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setEmail(String text) {
		email.setText(text);	
	}

}
