/**
 * 
 */
package io.crs.mws.client.app.spots.map;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;

/**
 * @author robi
 *
 */
public class SpotCreator extends Composite {
	private static Logger logger = Logger.getLogger(SpotCreator.class.getName());

	private static SpotCreatorUiBinder uiBinder = GWT.create(SpotCreatorUiBinder.class);

	interface SpotCreatorUiBinder extends UiBinder<Widget, SpotCreator> {
	}

	@UiField
	MaterialTextBox spotNameBox;

	@UiField
	MaterialLink saveLink;

	@UiField
	MaterialLink closeLink;

	/**
	 * t
	 */
	public SpotCreator() {
		logger.log(Level.INFO, "SpotCreator()");
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void addSaveLinkClickHandler(ClickHandler handler) {
		saveLink.addClickHandler(handler);
	}

	public void addCloseLinkClickHandler(ClickHandler handler) {
		closeLink.addClickHandler(handler);
	}

	public String getSpotName() {
		return spotNameBox.getText();
	}
}