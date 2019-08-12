/**
 * 
 */
package io.crs.mws.client.app.spots.list;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import io.crs.mws.shared.dto.WindspotDto;

/**
 * @author robi
 *
 */
public class SpotsListView extends ViewWithUiHandlers<SpotsListUiHandlers> implements SpotsListPresenter.MyView {
	private static Logger logger = Logger.getLogger(SpotsListView.class.getName());

	interface Binder extends UiBinder<Widget, SpotsListView> {
	}

	/**
	* 
	*/
	@Inject
	SpotsListView(Binder uiBinder) {
		logger.info("SpotsListView()");

		initWidget(uiBinder.createAndBindUi(this));
	}

	/*
	 * private void resizePanel(Integer height) { Integer h = height - 280;
	 * materialCollection.setHeight(h.toString() + "px"); }
	 */
	@Override
	public void refreshPanel(Boolean showEditor) {
	}

	@Override
	public void setData(List<WindspotDto> data, String chatWebSafeKey) {
		// TODO Auto-generated method stub
		
	}
}