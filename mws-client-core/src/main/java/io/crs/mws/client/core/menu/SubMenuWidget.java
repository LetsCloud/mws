/**
 * 
 */
package io.crs.mws.client.core.menu;

import java.util.logging.Level;
import java.util.logging.Logger;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.html.UnorderedList;
import io.crs.mws.shared.dto.menu.MenuItemDto;

/**
 * @author CR
 *
 */
public class SubMenuWidget extends MaterialCollapsibleItem {
	private static Logger logger = Logger.getLogger(SubMenuWidget.class.getName());

	public SubMenuWidget(MenuItemDto menuItem) {
		super();
		logger.log(Level.INFO, "SubMenuWidget()");
		init(menuItem);
	}

	private void init(MenuItemDto menuItem) {
//		logger.log(Level.INFO, "SubMenuWidget.init()");

		MaterialLink headerLink = new MaterialLink();
		if (menuItem.getIcon() != null)
			headerLink.setIconType(IconType.valueOf(menuItem.getIcon()));
		headerLink.setText(menuItem.getText());

		MaterialCollapsibleHeader collapsibleHeader = new MaterialCollapsibleHeader();
		collapsibleHeader.add(headerLink);
		collapsibleHeader.setWaves(WavesType.DEFAULT);
		add(collapsibleHeader);

		UnorderedList unorderedList = new UnorderedList();
		for (MenuItemDto item : menuItem.getItems()) {
			MaterialLink link = new MaterialLink();
			link.setText(item.getText());
			link.setTargetHistoryToken(item.getNameToken());
			link.setWaves(WavesType.DEFAULT);
			unorderedList.add(link);
		}

		MaterialCollapsibleBody collapsibleBody = new MaterialCollapsibleBody();
		collapsibleBody.add(unorderedList);

		add(collapsibleBody);
	}
}
