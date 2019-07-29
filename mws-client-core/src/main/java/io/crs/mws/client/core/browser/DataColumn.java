/**
 * 
 */
package io.crs.mws.client.core.browser;

import java.util.Comparator;

import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.table.cell.TextColumn;
import io.crs.mws.shared.dto.BaseDto;

/**
 * Adatoszlop beállítás.
 * 
 * @author robi
 *
 */
public class DataColumn<T extends BaseDto> extends TextColumn<T> {

	public interface ValueFunc<T extends BaseDto> {
		String getValue(T object);
	}

	private final ValueFunc<T> valueFunc;
	private boolean sortable = false;

	public DataColumn(ValueFunc<T> valueFunc) {
		this.valueFunc = valueFunc;
	}

	public DataColumn(ValueFunc<T> valueFunc, Comparator<? super RowComponent<T>> sortComparator) {
		this(valueFunc);
		this.sortable = true;
		this.sortComparator(sortComparator);
	}

	public DataColumn(ValueFunc<T> valueFunc, Comparator<? super RowComponent<T>> sortComparator, HideOn hideOn) {
		this(valueFunc, sortComparator);
		this.hideOn(hideOn);
	}

	@Override
	public String getValue(T object) {
		return valueFunc.getValue(object);
	}

	@Override
	public boolean sortable() {
		return sortable;
	}

}
