/**
 * 
 */
package io.crs.mws.client.core.ui.filter;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialRow;
import io.crs.mws.client.core.i18n.CoreMessages;

/**
 * @author robi
 *
 */
public abstract class AbstractFilterView extends ViewWithUiHandlers<AbstractFilterUiHandlers>
		implements AbstractFilterPresenter.MyView {
	private static Logger logger = Logger.getLogger(AbstractFilterView.class.getName());

	interface Binder extends UiBinder<Widget, AbstractFilterView> {
	}

	private static Binder uiBinder = GWT.create(Binder.class);

	@UiField
	protected MaterialCollapsibleHeader collapsibleHeader;

	@UiField
	protected MaterialCollapsibleBody collapsibleBody;
	
	@UiField
	protected MaterialRow controlPanel;
	
	protected MaterialCheckBox onlyActiveCheckBox;
	
	private MaterialChip onlyActiveChip;

	protected final CoreMessages i18nCore;

	public AbstractFilterView(CoreMessages i18nCore) {
		logger.info("AbstractFilterView()");
		initWidget(uiBinder.createAndBindUi(this));
		this.i18nCore = i18nCore;		
	}

	/**
	 * 
	 */
	@Override
	public void buildView() {
		// First, the input components must be initialized
		initView();
		// than will be placed on the desired layout
		createLayout();
	}

	/**
	 * 
	 */
	protected void initView() {
		initOnlyActiveFilter();
	}

	/**
	 * 
	 */
	protected void createLayout() {
		setOnlyActiveLayour();
	}

	protected void setOnlyActiveLayour() {
		onlyActiveCheckBox.setGrid("s12 m6");
		controlPanel.add(onlyActiveCheckBox);
	}

	private void initOnlyActiveFilter() {
		onlyActiveChip = new MaterialChip();
		onlyActiveChip.setText(i18nCore.roomTypesTableOnlyActive());

		onlyActiveCheckBox = new MaterialCheckBox();
		onlyActiveCheckBox.setText(i18nCore.roomTypesTableOnlyActive());
		onlyActiveCheckBox.setValue(true);
		onlyActiveCheckBox.addValueChangeHandler(e -> {
			setOnlyActiveChip(e.getValue());
			getUiHandlers().filterChange();
		});

		setOnlyActiveChip(onlyActiveCheckBox.getValue());
	}

	private void setOnlyActiveChip(Boolean onlyActive) {
		if (onlyActive) {
			collapsibleHeader.add(onlyActiveChip);
		} else {
			collapsibleHeader.remove(onlyActiveChip);
		}
	}

	@Override
	public Boolean isOnlyActive() {
		return onlyActiveCheckBox.getValue();
	}
	
	public void disableOnlyActive() {
		onlyActiveCheckBox.setValue(false);
		setOnlyActiveChip(false);
		onlyActiveCheckBox.setVisible(false);
	}
}