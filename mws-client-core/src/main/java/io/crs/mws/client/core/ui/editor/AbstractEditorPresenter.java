/**
 * 
 */
package io.crs.mws.client.core.ui.editor;

import static io.crs.mws.shared.api.ApiParameters.WEBSAFEKEY;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.Strings;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public abstract class AbstractEditorPresenter<T extends BaseDto, V extends AbstractEditorView<T>, P extends Proxy<?>>
		extends Presenter<V, P> implements AbstractEditorUiHandlers<T> {
	private static Logger logger = Logger.getLogger(AbstractEditorPresenter.class.getName());

	protected Map<String, String> filters = new HashMap<String, String>();

	private final PlaceManager placeManager;

	public AbstractEditorPresenter(EventBus eventBus, PlaceManager placeManager, V view, P proxy) {
		super(eventBus, view, proxy);
		this.placeManager = placeManager;
	}

	public AbstractEditorPresenter(EventBus eventBus, PlaceManager placeManager, V view, P proxy,
			GwtEvent.Type<RevealContentHandler<?>> slot) {
		super(eventBus, view, proxy, null, slot);
		this.placeManager = placeManager;
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		logger.info("AppUserEditorPresenter().prepareFromRequest()");
		filters.clear();
		Set<String> params = request.getParameterNames();
		for (String param : params) {
			filters.put(param, request.getParameter(param, null));
		}
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		loadData();
	}

	protected abstract void loadData();

	protected Boolean isNew() {
		return Strings.isNullOrEmpty(filters.get(WEBSAFEKEY));
	}

	protected void create() {
		logger.info("AbstractEditorPresenter().create()");
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
