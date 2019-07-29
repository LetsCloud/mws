/**
 * 
 */
package io.crs.mws.client.core.ui.table;

import com.google.gwt.dom.client.Document;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.helper.UiHelper;

/**
 * @author CR
 *
 */
public class MaterialListItem extends MaterialWidget {

	public MaterialListItem() {
		super(Document.get().createDivElement(), "hbox-xs");
		UiHelper.addMousePressedHandlers(this);
	}

}
