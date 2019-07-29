/**
 * 
 */
package io.crs.mws.client.adm.browser.globalconfig;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import io.crs.mws.client.adm.i18n.AdminMessages;
import io.crs.mws.client.core.browser.AbstractBrowserView;
import io.crs.mws.client.core.browser.ActionColumn;
import io.crs.mws.client.core.browser.DataColumn;
import io.crs.mws.shared.dto.GlobalConfigDto;

/**
 * @author robi
 *
 */
public class GlobalConfigBrowserView extends ViewWithUiHandlers<GlobalConfigBrowserUiHandlers>
		implements GlobalConfigBrowserPresenter.MyView {
	private static Logger logger = Logger.getLogger(GlobalConfigBrowserView.class.getName());

	private final AbstractBrowserView<GlobalConfigDto> table;

	private final AdminMessages i18n;

	/**
	* 
	*/
	@Inject
	GlobalConfigBrowserView(AbstractBrowserView<GlobalConfigDto> table, AdminMessages i18n) {
		logger.info("GlobalConfigBrowserView()");
		initWidget(table);

		this.table = table;
		this.i18n = i18n;

		bindSlot(GlobalConfigBrowserPresenter.SLOT_FILTER, table.getFilterPanel());
		bindSlot(GlobalConfigBrowserPresenter.SLOT_EDITOR, table.getEditorPanel());

		initTable();
	}

	private void initTable() {

		table.setTableTitle(i18n.globalConfigBrowserTitle());

		table.getAddButton().addClickHandler(e -> {
			getUiHandlers().addNew();
		});

		table.getDeleteIcon().addClickHandler(e -> {
			getUiHandlers().delete(table.getSelectedItems());
		});

		// Key Column
		table.addColumn(
				new DataColumn<GlobalConfigDto>((object) -> object.getCode(),
						(o1, o2) -> o1.getData().getCode().compareToIgnoreCase(o2.getData().getCode())),
				i18n.globalConfigBrowserFieldCode());

		// Name Column
		table.addColumn(
				new DataColumn<GlobalConfigDto>((object) -> object.getValue(),
						(o1, o2) -> o1.getData().getValue().compareToIgnoreCase(o2.getData().getValue())),
				i18n.globalConfigBrowserFieldValue());

		// Edit Column
		table.addColumn(new ActionColumn<GlobalConfigDto>((object) -> getUiHandlers().edit(object)));
	}

	@Override
	public void setData(List<GlobalConfigDto> data) {
		table.setData(data);
	}
}
