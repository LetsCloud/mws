/**
 * 
 */
package io.crs.mws.client.core.ui.filter;

import java.util.logging.Logger;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.ui.filter.FilterChangeEvent.DataTable;

/**
 * @author robi
 *
 */
public abstract class AbstractFilterPresenter<V extends AbstractFilterPresenter.MyView> extends PresenterWidget<V>
		implements AbstractFilterUiHandlers {
	private static Logger logger = Logger.getLogger(AbstractFilterPresenter.class.getName());

	public interface MyView extends View {
		void buildView();

		void reset();

		Boolean isOnlyActive();
	}

	protected final CurrentUser currentUser;

	public AbstractFilterPresenter(EventBus eventBus, V view, CurrentUser currentUser) {
		super(eventBus, view);
		logger.info("AbstractFilterPresenter()");
		this.currentUser = currentUser;
	}

	@Override
	public void onBind() {
		super.onBind();
		getView().buildView();
	}

	@Override
	public void filterChange() {
		FilterChangeEvent.fire(AbstractFilterPresenter.this, DataTable.ROOM_TYPE);
	}

	public Boolean isOnlyActive() {
		return getView().isOnlyActive();
	}
}
