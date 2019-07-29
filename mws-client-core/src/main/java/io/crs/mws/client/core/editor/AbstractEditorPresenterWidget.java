/**
 * 
 */
package io.crs.mws.client.core.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public abstract class AbstractEditorPresenterWidget<T extends BaseDto, V extends AbstractEditorView<T>>
		extends AbstractDisplayPresenterWidget<V> implements AbstractEditorUiHandlers<T> {
	private static Logger logger = Logger.getLogger(AbstractEditorPresenterWidget.class.getName());

	protected Map<String, String> filters = new HashMap<String, String>();
	
	private final PlaceManager placeManager;

	public AbstractEditorPresenterWidget(EventBus eventBus, PlaceManager placeManager, V view) {
		super(eventBus, view);
		this.placeManager = placeManager;
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		loadData();
	}

	protected abstract void loadData();

	protected void create() {
		logger.info("AbstractEditorPresenterWidget().create()");
		T dto = createDto();
		getView().edit(dto);
	}

	protected abstract T createDto();

	@Override
	public void cancel() {
		getView().close();
		placeManager.navigateBack();
	}
}
