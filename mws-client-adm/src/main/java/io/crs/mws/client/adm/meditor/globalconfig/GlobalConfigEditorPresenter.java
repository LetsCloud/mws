/**
 * 
 */
package io.crs.mws.client.adm.meditor.globalconfig;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import io.crs.mws.client.adm.NameTokens;
import io.crs.mws.client.adm.config.system.SystemConfigPresenter;
import io.crs.mws.client.core.config.AbstractConfigPresenter;
import io.crs.mws.client.core.meditor.AbstractMeditorPresenter;
import io.crs.mws.client.core.meditor.MeditorView;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.service.GlobalConfigService;
import io.crs.mws.shared.dto.EntityPropertyCode;
import io.crs.mws.shared.dto.GlobalConfigDto;

/**
 * @author robi
 *
 */
public class GlobalConfigEditorPresenter
		extends AbstractMeditorPresenter<GlobalConfigDto, GlobalConfigEditorPresenter.MyView>
		implements GlobalConfigEditorUiHandlers {
	private static Logger logger = Logger.getLogger(GlobalConfigEditorPresenter.class.getName());

	private static final GlobalConfigService GLOBALCONFIG_SERVICE = GWT.create(GlobalConfigService.class);

	public interface MyView extends MeditorView<GlobalConfigDto>, HasUiHandlers<GlobalConfigEditorUiHandlers> {

		void displayError(EntityPropertyCode code, String message);
	}

	private final PlaceManager placeManager;
	private final CurrentUser currentUser;

	@Inject
	GlobalConfigEditorPresenter(EventBus eventBus, PlaceManager placeManager, MyView view, CurrentUser currentUser) {
		super(eventBus, view);
		logger.info("GlobalConfigEditorPresenter()");

		this.placeManager = placeManager;
		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

	@Override
	protected GlobalConfigDto createDto() {
		GlobalConfigDto dto = new GlobalConfigDto();
		return dto;
	}

	@Override
	public void saveDto(GlobalConfigDto dto) {
		GLOBALCONFIG_SERVICE.saveOrCreate(dto, new MethodCallback<GlobalConfigDto>() {

			@Override
			public void onSuccess(Method method, GlobalConfigDto response) {
				logger.info("GlobalConfigBrowserPresenter.loadData().onSuccess()");
				getView().close();
				PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.SYSTEM_CONFIG)
						.with(AbstractConfigPresenter.PLACE_PARAM, SystemConfigPresenter.GLOBAL_CONFIGS).build();
				placeManager.revealPlace(placeRequest);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.info("GlobalConfigBrowserPresenter.loadData().onFailure()");
			}
		});
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}
}