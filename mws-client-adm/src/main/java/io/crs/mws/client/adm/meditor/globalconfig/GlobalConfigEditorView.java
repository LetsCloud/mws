/**
 * 
 */
package io.crs.mws.client.adm.meditor.globalconfig;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import io.crs.mws.client.adm.i18n.AdminMessages;
import io.crs.mws.client.core.message.MessageData;
import io.crs.mws.shared.dto.EntityPropertyCode;
import io.crs.mws.shared.dto.GlobalConfigDto;

/**
 * @author robi
 *
 */
public class GlobalConfigEditorView extends ViewWithUiHandlers<GlobalConfigEditorUiHandlers>
		implements GlobalConfigEditorPresenter.MyView, Editor<GlobalConfigDto> {
	private static Logger logger = Logger.getLogger(GlobalConfigEditorView.class.getName());

	interface Binder extends UiBinder<Widget, GlobalConfigEditorView> {
	}

	interface Driver extends SimpleBeanEditorDriver<GlobalConfigDto, GlobalConfigEditorView> {
	}

	private final Driver driver;

	@UiField
	MaterialDialog modal;

	@Ignore
	@UiField
	MaterialTitle title;

	@UiField
	MaterialTextBox code, value;

	@UiField
	MaterialButton saveButton;

	private final AdminMessages i18n;

	/**
	* 
	*/
	@Inject
	GlobalConfigEditorView(Binder uiBinder, Driver driver, AdminMessages i18n) {
		logger.info("GlobalConfigEditorView()");

		this.i18n = i18n;

		initWidget(uiBinder.createAndBindUi(this));

// saveButton.setBackgroundColor(Color.GREY);

		this.driver = driver;
		driver.initialize(this);
	}

	@Override
	public void open(GlobalConfigDto dto) {
		if (dto.getId() == null) {
			title.setTitle(i18n.globalConfigCreatorTitle());
		} else {
			title.setTitle(i18n.globalConfigEditorTitle());
		}
		driver.edit(dto);

		modal.open();

		Timer t = new Timer() {
			@Override
			public void run() {
				value.setFocus(true);
			}
		};
		t.schedule(100);
	}

	@Override
	public void close() {
		modal.close();
	}

	@Override
	public void displayError(EntityPropertyCode code, String message) {
// TODO Auto-generated method stub

	}

	@UiHandler("saveButton")
	void onSaveClick(ClickEvent event) {
		getUiHandlers().save(driver.flush());
	}

	@UiHandler("cancelButton")
	void onCancelClick(ClickEvent event) {
		getUiHandlers().cancel();
	}

	@Override
	public void showMessage(MessageData message) {
		// TODO Auto-generated method stub
		
	}
}
