/**
 * 
 */
package io.crs.mws.client.core.browser;

import gwt.material.design.client.ui.table.cell.Column;
import io.crs.mws.shared.dto.BaseDto;

/**
 * @author robi
 *
 */
public abstract class AbstractColumnConfig<T extends BaseDto> {
	private String header;
	private Column<T, ?> column;
	private Boolean hidden;

	public AbstractColumnConfig(Column<T, ?> column) {
		this.header = "";
		this.column = column;
		this.hidden = false;
	}

	public AbstractColumnConfig(String header, Column<T, ?> column) {
		this(column);
		this.header = header;
	}

	public AbstractColumnConfig(String header, Column<T, ?> column, Boolean hidden) {
		this(header, column);
		this.hidden = hidden;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Column<T, ?> getColumn() {
		return column;
	}

	public void setColumn(Column<T, ?> column) {
		this.column = column;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}
