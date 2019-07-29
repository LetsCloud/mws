/**
 * 
 */
package io.crs.mws.client.core.meditor;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

import io.crs.mws.client.core.event.DisplayMessageEvent;
import io.crs.mws.client.core.event.DisplayMessageEvent.DisplayMessageHandler;
import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public abstract class AbstractMeditorPresenter<T extends BaseDto, V extends MeditorView<T>> extends PresenterWidget<V>
		implements MeditorUiHandlers<T>, DisplayMessageHandler {

	public AbstractMeditorPresenter(EventBus eventBus, V view) {
		super(eventBus, view);
		// TODO Auto-generated constructor stub
	}

	protected abstract T createDto();

	@Override
	public void create() {
		getView().open(createDto());
	}

	@Override
	public void edit(T dto) {
		getView().open(dto);
	}

	@Override
	public void onDisplayMessage(DisplayMessageEvent event) {
		getView().showMessage(event.getMessage());
	}

	protected abstract void saveDto(T dto);

	@Override
	public void save(T dto) {
		saveDto(dto);
	}

	@Override
	public void cancel() {
		getView().close();
	}
}
