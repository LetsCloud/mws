/**
 * 
 */
package io.crs.mws.client.core.filter;

import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialTextBox;

/**
 * @author robi
 *
 */
public class TextFilter extends BaseFilter {

	MaterialTextBox textBox = new MaterialTextBox();

	@Inject
	TextFilter() {
		initWidget(textBox);
		initTextBox();
	}

	@Override
	protected MaterialWidget getFilterWidget() {
		return textBox;
	}

	public String getValue() {
		return textBox.getValue();
	}

	public void setValue(String value) {
		textBox.setValue(value);
	}

	public void setFilterLabel(String label) {
		textBox.setLabel(label);
	}

	private void initTextBox() {
		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (Strings.isNullOrEmpty(event.getValue()))
					setChipText("");
				else
					setChipText(chipLabel + event.getValue());
			}
		});
	}

	public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> handler) {
		return textBox.addValueChangeHandler(handler);
	}

	public void setFilterMarginTop(double margin) {
		textBox.setMarginTop(margin);
	}

	public void setFilterPlaceholder(String text) {
		textBox.setPlaceholder(text);
	}

	public void setFilterHeight(double value, Unit unit) {
		textBox.getValueBoxBase().getElement().getStyle().setHeight(value, unit);
	}
}
