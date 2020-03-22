/**
 * 
 */
package io.crs.mws.client.app;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;

import gwt.material.design.client.constants.IconType;

import io.crs.mws.client.app.i18n.AppMessages;
import io.crs.mws.client.app.resources.AppResources;
import io.crs.mws.client.core.CoreNameTokens;
import io.crs.mws.client.core.app.AbstractAppPresenter;
import io.crs.mws.client.core.app.AppServiceWorkerManager;
import io.crs.mws.client.core.menu.MenuPresenter;
import io.crs.mws.client.core.model.BreadcrumbConfig;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.shared.cnst.MenuItemType;
import io.crs.mws.shared.cnst.SubSystem;
import io.crs.mws.shared.dto.menu.MenuItemDto;

/**
 * @author CR
 *
 */
public class AppPresenter extends AbstractAppPresenter<AppPresenter.MyProxy> {

	private final AppMessages i18n;
	private final AppResources resources;

	@ProxyStandard
	interface MyProxy extends Proxy<AppPresenter> {
	}

	@Inject
	AppPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, AppMessages i18n,
			CurrentUser currentUser, MenuPresenter menuPresenter, AppData appData, AppServiceWorkerManager swManager,
			AppResources resources) {
		super(eventBus, view, proxy, placeManager, menuPresenter, currentUser, SubSystem.APP, swManager);

		this.i18n = i18n;
		this.resources = resources;

		breadcrumbConfigs.add(new BreadcrumbConfig(0, IconType.HOME, "", CoreNameTokens.HOME));
	}

	@Override
	protected void onBind() {
		super.onBind();
		getMenuPresenter().setMenuItems(createMenuitems());
		getMenuPresenter().setProfileBackground(resources.profileBackgroundImg());
	}

	private List<MenuItemDto> createMenuitems() {
		List<MenuItemDto> menuItems = new ArrayList<MenuItemDto>();

		// *********************
		// Wall
		// *********************
		MenuItemDto wallMenuItem = new MenuItemDto();
		wallMenuItem.setIndex(1);
		wallMenuItem.setType(MenuItemType.MENU_ITEM);
		wallMenuItem.setIcon(IconType.TEXTSMS.name());
		wallMenuItem.setText(i18n.mainMenuItemWall());
		wallMenuItem.setNameToken(CoreNameTokens.HOME);
		menuItems.add(wallMenuItem);

		// *********************
		// Wall
		// *********************
		MenuItemDto spotsMenuItem = new MenuItemDto();
		spotsMenuItem.setIndex(2);
		spotsMenuItem.setType(MenuItemType.MENU_ITEM);
		spotsMenuItem.setIcon(IconType.LOCATION_ON.name());
		spotsMenuItem.setText(i18n.mainMenuItemSpots());
		spotsMenuItem.setNameToken(NameTokens.SPOTS);
		menuItems.add(spotsMenuItem);

		// *********************
		// Office functions
		// *********************
		MenuItemDto officeSubMenu = new MenuItemDto();
		officeSubMenu.setIndex(3);
		officeSubMenu.setType(MenuItemType.SUB_MENU);
		officeSubMenu.setIcon(IconType.SETTINGS.name());
		officeSubMenu.setText(i18n.mainMenuOffice());
		officeSubMenu.setItems(new ArrayList<MenuItemDto>());
		menuItems.add(officeSubMenu);

		MenuItemDto organizationsMenuItem = new MenuItemDto();
		organizationsMenuItem.setIndex(1);
		organizationsMenuItem.setType(MenuItemType.MENU_ITEM);
		organizationsMenuItem.setText(i18n.menuItemOrganizations());
		organizationsMenuItem.setNameToken(NameTokens.SETUP);
		officeSubMenu.addItem(organizationsMenuItem);

		return menuItems;
	}
}
