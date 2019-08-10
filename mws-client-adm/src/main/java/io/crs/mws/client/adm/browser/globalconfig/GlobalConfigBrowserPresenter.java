/**
 * 
 */
package io.crs.mws.client.adm.browser.globalconfig;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import io.crs.mws.client.adm.meditor.globalconfig.GlobalConfigEditorFactory;
import io.crs.mws.client.adm.meditor.globalconfig.GlobalConfigEditorPresenter;
import io.crs.mws.client.core.browser.AbstractBrowserPresenter;
import io.crs.mws.client.core.event.RefreshTableEvent.TableType;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.service.GlobalConfigService;
import io.crs.mws.shared.dto.GlobalConfigDto;

/**
 * @author robi
 *
 */
public class GlobalConfigBrowserPresenter extends AbstractBrowserPresenter<GlobalConfigDto, GlobalConfigBrowserPresenter.MyView>
		implements GlobalConfigBrowserUiHandlers {
	private static Logger logger = Logger.getLogger(GlobalConfigBrowserPresenter.class.getName());

	private static final GlobalConfigService GLOBALCONFIG_SERVICE = GWT.create(GlobalConfigService.class);

	public interface MyView extends View, HasUiHandlers<GlobalConfigBrowserUiHandlers> {
		void setData(List<GlobalConfigDto> data);
	}

	public static final SingleSlot<PresenterWidget<?>> SLOT_FILTER = new SingleSlot<>();
	public static final SingleSlot<PresenterWidget<?>> SLOT_EDITOR = new SingleSlot<>();

	private final GlobalConfigEditorPresenter editor;

	private final CurrentUser currentUser;

	@Inject
	GlobalConfigBrowserPresenter(EventBus eventBus, PlaceManager placeManager, MyView view,
			CurrentUser currentUser,
			GlobalConfigEditorFactory editorFactory) {
		super(eventBus, view, placeManager);

		this.editor = editorFactory.createMarketGroupEditor();
		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		setInSlot(SLOT_EDITOR, editor);
	}

	@Override
	protected void loadData() {
		logger.info("GlobalConfigBrowserPresenter.loadData()");
		GLOBALCONFIG_SERVICE.getAll(new MethodCallback<List<GlobalConfigDto>>() {

			@Override
			public void onSuccess(Method method, List<GlobalConfigDto> response) {
				logger.info("GlobalConfigBrowserPresenter.loadData().onSuccess()");
				getView().setData(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.info("GlobalConfigBrowserPresenter.loadData().onFailure()");
			}
		});		
	}

	@Override
	public void addNew() {
		editor.create();
	}

	@Override
	public void edit(GlobalConfigDto dto) {
		editor.edit(dto);
	}

	@Override
	protected void deleteData(String webSafeKey) {
/*		resourceDelegate.withCallback(new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				loadData();
			}
		}).delete(webSafeKey); */
	}

	@Override
	protected String getCreatorNameToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEditorNameToken() {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<GlobalConfigDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected TableType getTableType() {
		// TODO Auto-generated method stub
		return null;
	}
}