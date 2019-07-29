/**
 * 
 */
package io.crs.mws.client.core.ui.table;

import com.google.gwt.dom.client.Document;

import gwt.material.design.client.base.MaterialWidget;

/**
 * @author CR
 *
 */
public class MaterialListItemColumn extends MaterialWidget {

	public MaterialListItemColumn() {
		super(Document.get().createDivElement(), "hbox-column");
	}

}
