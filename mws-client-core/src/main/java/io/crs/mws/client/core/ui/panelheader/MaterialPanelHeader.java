/**
 * 
 */
package io.crs.mws.client.core.ui.panelheader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardTitle;

/**
 * @author CR
 *
 */
public class MaterialPanelHeader extends Composite {

	private static MaterialPanelHeaderUiBinder uiBinder = GWT.create(MaterialPanelHeaderUiBinder.class);

	interface MaterialPanelHeaderUiBinder extends UiBinder<Widget, MaterialPanelHeader> {
	}

	@UiField
	MaterialCardTitle title;

	/**
	 */
	public MaterialPanelHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setTitleText(String text) {
		title.setText(text);
	}
}
