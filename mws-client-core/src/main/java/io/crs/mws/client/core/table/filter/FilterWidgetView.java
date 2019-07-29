/**
 * 
 */
package io.crs.mws.client.core.table.filter;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import io.crs.mws.client.core.i18n.CoreMessages;

/**
 * @author robi
 *
 */
public class FilterWidgetView extends ViewWithUiHandlers<FilterWidgetUiHandlers>
		implements FilterWidgetPresenter.MyView {
	private static Logger logger = Logger.getLogger(FilterWidgetView.class.getName());

	interface Binder extends UiBinder<Widget, FilterWidgetView> {
	}

	private final CoreMessages i18nCore;

	private MaterialChip allItemsChip;

	@UiField
	MaterialCollapsibleHeader collapsibleHeader;

	@UiField
	MaterialChip hotelChip;

	@UiField
	MaterialCheckBox onlyActiveCheckBox;

	@Inject
	FilterWidgetView(Binder uiBinder, CoreMessages i18nCore) {
		logger.info("FilterWidgetView()");

		initWidget(uiBinder.createAndBindUi(this));

		this.i18nCore = i18nCore;

		init();
	}

	private void init() {
		allItemsChip = new MaterialChip(i18nCore.roomTypesTableOnlyActive());
		setOnlyActiveCheckBox(onlyActiveCheckBox.getValue());

		onlyActiveCheckBox.addValueChangeHandler(e -> {
			setOnlyActiveCheckBox(e.getValue());
			getUiHandlers().filterChange();
		});
	}

	private void setOnlyActiveCheckBox(Boolean onlyActive) {
		if (onlyActive) {
			collapsibleHeader.add(allItemsChip);
		} else {
			collapsibleHeader.remove(allItemsChip);
		}
	}

	@Override
	public Boolean isOnlyActive() {
		return onlyActiveCheckBox.getValue();
	}
}