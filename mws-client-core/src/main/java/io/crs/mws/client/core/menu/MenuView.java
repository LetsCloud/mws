/**
 * 
 */
package io.crs.mws.client.core.menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import gwt.material.design.addins.client.avatar.MaterialAvatar;
import gwt.material.design.addins.client.sideprofile.MaterialSideProfile;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSideNavPush;

import io.crs.mws.client.core.event.ContentPushEvent.MenuState;
import io.crs.mws.shared.cnst.MenuItemType;
import io.crs.mws.shared.dto.menu.MenuItemDto;

/**
 * @author CR
 *
 */
public class MenuView extends ViewWithUiHandlers<MenuUiHandlers> implements MenuPresenter.MyView {
	private static Logger logger = Logger.getLogger(MenuView.class.getName());

	interface Binder extends UiBinder<Widget, MenuView> {
	}

	@UiField
	MaterialHeader header;

	@UiField
	HTMLPanel brandPanel;

	@UiField
	MaterialNavBar navBar;

	@UiField
	MaterialSideNavPush sideNav;

	@UiField
	MaterialSideProfile sideProfile;

	@UiField
	SimplePanel navBarSlot;

	@UiField
	MaterialLink userLink, logoutLink;

	@UiField
	MaterialImage userImage;

	@UiField
	MaterialAvatar avatar;
	
	private List<MaterialLink> singleLinks = new ArrayList<MaterialLink>();
	private List<MaterialCollapsible> collapsibles = new ArrayList<MaterialCollapsible>();

	@Inject
	MenuView(Binder uiBinder) {
		logger.info("MenuView()");

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onAttach() {
		super.onAttach();

		userLink.setFontSize(14, Unit.PX);

		sideNav.addOpenedHandler(event -> getUiHandlers().setContentPush(MenuState.OPEN));
		sideNav.addClosedHandler(event -> getUiHandlers().setContentPush(MenuState.CLOSE));
	}

	@Override
	public void setPageTitle(String title, String description) {
		// this.title.setText(title);
		// this.description.setText(description);

		// MaterialAnimator.animate(Transition.BOUNCEINLEFT, this.title, 1000);
		// MaterialAnimator.animate(Transition.BOUNCEINLEFT, this.description,
		// 1000);

	}

	@Override
	public void checkPermittedWidgets() {
		// hkMaterialCollapsibleItem.setVisible(getUiHandlers().canReveal(Permissions.PERM_MENU_HK));
	}

	@Override
	public void setUserName(String userName) {
//		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		userLink.setText(userName);
	}

	@Override
	public void setBusinessDate(Date businessDate) {
		// profileWidget.setBusinessDate(businessDate);
	}

	@UiHandler("logoutLink")
	void onLogout(ClickEvent event) {
		sideNav.close();
		getUiHandlers().logout();
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == MenuPresenter.SLOT_NAVBAR) {
			navBarSlot.setWidget(content);
		}
	}

	@Override
	public void setMenuItems(List<MenuItemDto> menuItems) {
		MenuItemType prevType = null;

		singleLinks.clear();
		collapsibles.clear();
		MaterialCollapsible collapsible = null;

		for (MenuItemDto menuItem : menuItems) {
			if (menuItem.getType().equals(MenuItemType.MENU_ITEM))
				sideNav.add(createMenuItem(menuItem));

			if (menuItem.getType().equals(MenuItemType.SUB_MENU)) {
				if ((prevType == null) || (prevType.equals(MenuItemType.MENU_ITEM))) {
					collapsible = new MaterialCollapsible();
					collapsibles.add(collapsible);
					sideNav.add(collapsible);
				}
				collapsible.add(new SubMenuWidget(menuItem));
			}
			prevType = menuItem.getType();
		}
	}

	private MaterialLink createMenuItem(MenuItemDto menuItem) {
		MaterialLink link = new MaterialLink();
		link.setIconPosition(IconPosition.LEFT);
		if (menuItem.getIcon() != null)
			link.setIconType(IconType.valueOf(menuItem.getIcon()));
		link.setText(menuItem.getText());
		link.setTargetHistoryToken(menuItem.getNameToken());
		singleLinks.add(link);
		return link;
	}

	@Override
	public void inactivateSingleLinks() {
		logger.info("MenuView().inactivateSingleLinks");
		for (MaterialLink link : singleLinks) {
			logger.info("MenuView().inactivateSingleLinks->" + link.getText());
			link.getParent().removeStyleName("active");
		}
	}

	@Override
	public void closeCollapisbles() {
		logger.info("MenuView().closeCollapisbles");
		for (MaterialCollapsible collapsible : collapsibles) {
			logger.info("MenuView().closeCollapisbles->close=" + collapsible.getTitle());
			collapsible.closeAll();
		}
	}

	@Override
	public void setAppFullname(String fullname) {
		brandPanel.clear();
		brandPanel.add(new HTML(fullname));
	}

	@Override
	public void setUserImageUrl(String url, String fullname) {
		avatar.setVisible(false);
		userImage.setVisible(false);
		if (Strings.isNullOrEmpty(url)) {
			avatar.setVisible(true);
			avatar.setValue(fullname, false, true);
		} else {
			userImage.setVisible(true);
			userImage.setUrl(url);
		}
	}

	@Override
	public void setProfileBackground(ImageResource resource) {
		sideProfile.setResource(resource);
	}
}
