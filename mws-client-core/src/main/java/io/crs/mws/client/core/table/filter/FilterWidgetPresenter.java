/**
 * 
 */
package io.crs.mws.client.core.table.filter;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.ui.filter.FilterChangeEvent;
import io.crs.mws.client.core.ui.filter.FilterChangeEvent.DataTable;

/**
 * @author robi
 *
 */
public class FilterWidgetPresenter extends PresenterWidget<FilterWidgetPresenter.MyView>
		implements FilterWidgetUiHandlers {
	private static Logger logger = Logger.getLogger(FilterWidgetPresenter.class.getName());

	public interface MyView extends View, HasUiHandlers<FilterWidgetUiHandlers> {
		Boolean isOnlyActive();
	}

	private final CurrentUser currentUser;

	@Inject
	FilterWidgetPresenter(EventBus eventBus, MyView view, CurrentUser currentUser) {
		super(eventBus, view);
		logger.info("FilterWidgetPresenter()");

		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

	@Override
	public void onReveal() {
		super.onReveal();
		logger.info("FilterWidgetPresenter().loadData()");
	}

	@Override
	public void filterChange() {
		logger.info("FilterWidgetPresenter().filterChange()");
		FilterChangeEvent.fire(FilterWidgetPresenter.this, DataTable.ROOM_TYPE);
	}
	
	public Boolean isOnlyActive() {
		return getView().isOnlyActive();
	}
}