/**
 * 
 */
package io.crs.mws.client.core.browser;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.SortDir;
import gwt.material.design.client.data.events.SetupEvent;
import gwt.material.design.client.data.events.SetupHandler;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.Column;
import io.crs.mws.client.core.message.MessageData;
import io.crs.mws.client.core.message.dialog.MessageDialogWidget;
import io.crs.mws.shared.dto.BaseDto;

/**
 * A törzsadatok táblázatos megjelenítését végző nézetek ős osztálya.
 * <p>
 * A nézet tartalmaz egy foglalatot a filter widgetnek, egy MaterialDataTable
 * táblázatot, egy foglalatot a modális editornak és a szükséges vezérlő
 * gombokat.
 * <p>
 * A filter és az editor widgeteket az utód osztály illeszti be igény szerint.
 * <p>
 * MaterialDataTable táblázat oszlopait ugyancsak az utőd állítja be igény
 * szerint.
 * 
 * @author robi
 *
 */
public class AbstractBrowserView<T extends BaseDto> extends Composite {
	private static Logger logger = Logger.getLogger(AbstractBrowserView.class.getName());

	interface Binder extends UiBinder<HTMLPanel, AbstractBrowserView<?>> {
	}

	public interface EditRow<O extends BaseDto> {
		void edit(O object);
	}

	private List<AbstractColumnConfig<T>> columnConfigs = new ArrayList<AbstractColumnConfig<T>>();

	@UiField
	SimplePanel filterPanel;

	@UiField
	MaterialDataTable<T> table;

	@UiField
	MaterialButton addButton;

	MaterialIcon deleteIcon;

	@UiField
	MessageDialogWidget messageDialog;

	@UiField
	SimplePanel editorPanel;

	/**
	* 
	*/
	@Inject
	AbstractBrowserView(Binder uiBinder) {
		logger.info("AbstractBrowserView()");

		initWidget(uiBinder.createAndBindUi(this));

		initTable();
	}

	private void initTable() {
		deleteIcon = new MaterialIcon(IconType.DELETE);

		table.addSetupHandler(new SetupHandler() {
			@Override
			public void onSetup(SetupEvent event) {
				setToolPanel(event.getScaffolding().getToolPanel());
			}
		});

		table.addColumnSortHandler(event -> {
			table.getView().refresh();
		});

		table.getView().refresh();
	}

	public List<AbstractColumnConfig<T>> getColumnConfigs() {
		return columnConfigs;
	}

	public void setColumnConfigs(List<AbstractColumnConfig<T>> columnConfigs) {
		this.columnConfigs = columnConfigs;
	}

	public void addColumnConfigs(AbstractColumnConfig<T> columnConfig) {
		this.columnConfigs.add(columnConfig);
	}

	public void clearColumnConfigs() {
		this.columnConfigs.clear();
	}

	public void addAllColumns() {
		table.removeColumns();
		for (AbstractColumnConfig<T> columnConfig : columnConfigs) {
			table.addColumn(columnConfig.getHeader(), columnConfig.getColumn());
		}
	}

	public void hideColumn(Integer index, Boolean hide) {
		if (columnConfigs.get(index).getHidden().equals(hide))
			return;

		if (hide) {
			removeColumn(index);
			return;
		}
		insertColumn(index);
	}

	private void removeColumn(Integer index) {
		columnConfigs.get(index).setHidden(true);
		int tempIndex = 0;
		for (int i = 0; i < index; i++) {
			if (!columnConfigs.get(i).getHidden())
				tempIndex++;
		}
		table.removeColumn(tempIndex);
	}

	private void insertColumn(Integer index) {
		int tempIndex = 0;
		for (int i = 0; i < index; i++) {
			if (!columnConfigs.get(i).getHidden())
				tempIndex++;
		}
		table.insertColumn(columnConfigs.get(index).getHeader(), tempIndex, columnConfigs.get(index).getColumn());
		columnConfigs.get(index).setHidden(false);
	}

	protected void setToolPanel(Panel toolPanel) {

		MaterialIcon menuIcon = new MaterialIcon(IconType.MORE_VERT);
		menuIcon.setActivates("dd-menu");

		MaterialDropDown<String> menuDropDown = new MaterialDropDown<String>();
		menuDropDown.setActivator("dd-menu");
		menuDropDown.setConstrainWidth(false);
		menuDropDown.setWidth("180px");

		MaterialLink pdfLink = new MaterialLink();
		pdfLink.setIconType(IconType.PICTURE_AS_PDF);
		pdfLink.setText("PDF export");
		menuDropDown.add(pdfLink);

		MaterialLink xlsLink = new MaterialLink();
		xlsLink.setIconType(IconType.DOCK);
		pdfLink.setText("XLS export");
		menuDropDown.add(xlsLink);

		toolPanel.add(deleteIcon);
		toolPanel.add(menuIcon);
		toolPanel.add(menuDropDown);

		table.getStretchIcon().setVisible(false);
		table.getColumnMenuIcon().setVisible(false);
	}

	/**
	 * A táblázat adatoszlopainak beállításást szolgáló metódus.
	 * 
	 * @param column A beszúrandó oszlop.
	 * @param title  Az oszlop felirata.
	 */
	public void addColumn(Column<T, ?> column, String title) {
		table.addColumn(title, column);
	}

	/**
	 * A táblázat vezérlő oszlopainak beállításást szolgáló metódus.
	 * 
	 * @param column A beszúrandó oszlop.
	 */
	public void addColumn(Column<T, ?> column) {
		table.addColumn(column);
	}

	public MaterialDataTable<T> getTable() {
		return table;
	}

	/**
	 * A presenter itt tölti fel adatokkal a nézetet.
	 * 
	 * @param data Az adat objektuumok listája.
	 */
	public void setData(List<T> data) {
		table.setVisibleRange(0, data.size());
		table.setRowData(0, data);
		if (!data.isEmpty()) {
			table.sort(0, SortDir.ASC);
		}
	}

	/**
	 * Visszaadja az új adat objektum gombot, hogy a presenter rákösse a mefelelő
	 * eseményt.
	 * 
	 * @return A gomb.
	 */
	public MaterialButton getAddButton() {
		return addButton;
	}

	/**
	 * Visszaadja a törlés ikont, hogy a presenter rákösse a mefelelő eseményt.
	 * 
	 * @return A törlés ikon.
	 */
	public MaterialIcon getDeleteIcon() {
		return deleteIcon;
	}

	/**
	 * Visszaadja a kiválasztott adat objektumok listáját.
	 * 
	 * @return A kiválasztott adat objektunok listája.
	 */
	public List<T> getSelectedItems() {
		return table.getSelectedRowModels(false);
	}

	/**
	 * Visszadja a filter widgetnek szánt foglalatot.
	 * 
	 * @return Filter widget foglalat.
	 */
	public SimplePanel getFilterPanel() {
		return filterPanel;
	}

	/**
	 * Visszadja a modal editor widgetnek szánt foglalatot.
	 * 
	 * @return Modal editor widget foglalat.
	 */
	public SimplePanel getEditorPanel() {
		return editorPanel;
	}

	public void setTableTitle(String title) {
		table.getTableTitle().setText(title);
	}

	public void showMessage(MessageData messageData) {
		messageDialog.showMessage(messageData);
	}
}
