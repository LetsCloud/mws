/**
 * 
 */
package io.crs.mws.client.app.spots;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialColumn;
import io.crs.mws.shared.dto.WindspotDto;

/**
 * @author robi
 *
 */
public class SpotsView extends ViewWithUiHandlers<SpotsUiHandlers> implements SpotsPresenter.MyView {
	private static Logger logger = Logger.getLogger(SpotsView.class.getName());

	interface Binder extends UiBinder<Widget, SpotsView> {
	}

	@UiField
	MaterialColumn leftColumn, rightColumn;
	
	@Inject
	SpotsView(Binder uiBinder) {
		logger.log(Level.INFO, "SpotsView");
		initWidget(uiBinder.createAndBindUi(this));
		
		leftColumn.setVisible(false);
		
		bindSlot(SpotsPresenter.SLOT_LIST, leftColumn);
		bindSlot(SpotsPresenter.SLOT_MAP, rightColumn);
	}

	@Override
	public void reveal(List<WindspotDto> windpots) {
	}
}