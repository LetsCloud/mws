package io.crs.mws.client.core.app;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.ui.MaterialBreadcrumb;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import io.crs.mws.client.core.model.BreadcrumbConfig;

public class AppView extends ViewImpl implements AbstractAppPresenter.MyView {
	private static Logger logger = Logger.getLogger(AppView.class.getName());

	public interface Binder extends UiBinder<Widget, AppView> {
	}

	@UiField
	MaterialRow main;

	@UiField
	HTMLPanel menu;

	@UiField
	MaterialLabel title, description;

	@UiField
	SimplePanel modalSlot;

	@UiField
	MaterialPanel breadcrumbsPanel;

	@Inject
	AppView(Binder uiBinder) {
		logger.info("ApplicationView()");
		initWidget(uiBinder.createAndBindUi(this));

		bindSlot(AbstractAppPresenter.SLOT_MENU, menu);
		bindSlot(AbstractAppPresenter.SLOT_MAIN, main);
		bindSlot(AbstractAppPresenter.SLOT_MODAL, modalSlot);
	}

	@Override
	public void displayUserName(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPageTitle(String title, String description) {
		this.title.setText(title);
		this.description.setText(description);
		breadcrumbsPanel.clear();
	}

	@Override
	public void setBreadcrumbs(List<BreadcrumbConfig> breadcrumbConfigs) {
		breadcrumbsPanel.clear();
		for (BreadcrumbConfig breadcrumbConfig : breadcrumbConfigs) {
			logger.info("ApplicationView().setBreadcrumbs()->breadcrumbConfig=" + breadcrumbConfig);
			MaterialBreadcrumb breadcrumb = new MaterialBreadcrumb(breadcrumbConfig.getIcon());
			ViewPort.when(Resolution.ALL_LAPTOP).then(viewPortChange -> {
				breadcrumb.setText(breadcrumbConfig.getText());
			});
			breadcrumb.setTargetHistoryToken(breadcrumbConfig.getTargetHistory());
			breadcrumbsPanel.add(breadcrumb);
		}
	}
}
