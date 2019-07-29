/**
 * 
 */
package io.crs.mws.client.core.menu;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

import io.crs.mws.client.core.NameTokens;
import io.crs.mws.client.core.event.ContentPushEvent;
import io.crs.mws.client.core.event.SetPageTitleEvent;
import io.crs.mws.client.core.event.ContentPushEvent.MenuState;
import io.crs.mws.client.core.event.SetPageTitleEvent.SetPageTitleHandler;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.security.HasPermissionsGatekeeper;
import io.crs.mws.client.core.util.OauthUtils;
import io.crs.mws.shared.cnst.MenuItemType;
import io.crs.mws.shared.dto.menu.MenuItemDto;

/**
 * @author CR
 *
 */
public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView>
		implements MenuUiHandlers, SetPageTitleHandler {
	private static Logger logger = Logger.getLogger(MenuPresenter.class.getName());

	public static final SingleSlot<PresenterWidget<?>> SLOT_NAVBAR = new SingleSlot<>();

	private final HasPermissionsGatekeeper menItemGatekeeper;

	interface MyView extends View, HasUiHandlers<MenuUiHandlers> {
		void checkPermittedWidgets();

		void setPageTitle(String title, String description);

		void setProfileBackground(ImageResource resource);

		void setUserName(String userName);

		void setUserImageUrl(String url, String fullname);

		void setBusinessDate(Date businessDate);

		void setMenuItems(List<MenuItemDto> menuItems);

		void inactivateSingleLinks();

		void closeCollapisbles();

		void setAppFullname(String fullname);
	}

	private final PlaceManager placeManager;
	private final CurrentUser currentUser;
	private final AppData appData;

	@Inject
	MenuPresenter(EventBus eventBus, MyView view, PlaceManager placeManager, CurrentUser currentUser, AppData appData,
			HasPermissionsGatekeeper menItemGatekeeper) {
		super(eventBus, view);
		logger.info("MenuPresenter()");

		this.placeManager = placeManager;
		this.currentUser = currentUser;
		this.appData = appData;
		this.menItemGatekeeper = menItemGatekeeper;

		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		addRegisteredHandler(SetPageTitleEvent.TYPE, this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		referesh();
		/*
		 * getView().checkPermittedWidgets();
		 * getView().setAccountName(currentUser.getAppUserDto().getAccountDto().getName(
		 * )); getView().setHotelName(currentUser.getCurrentHotelDto().getName());
		 * 
		 * getView().setBusinessDate(currentUser.getCurrentHotelDto().getBusinessDate())
		 * ;
		 */
	}

	@Override
	public void onSetPageTitle(SetPageTitleEvent event) {
		getView().setPageTitle(event.getTitle(), event.getDescription());
	}

	@Override
	public Boolean canReveal(String permission) {
		String[] permissions = { permission };
		menItemGatekeeper.withParams(permissions);
		return menItemGatekeeper.canReveal();
	}

	@Override
	public void setContentPush(MenuState menuState) {
		ContentPushEvent.fire(this, menuState);
	}

	public void setNavBarWidget(PresenterWidget<?> widget) {
		setInSlot(SLOT_NAVBAR, widget);

	}

	public void setMenuItems(List<MenuItemDto> menuItems) {
		getView().setMenuItems(menuItems);
	}

	public void adjustMenuItems(MenuItemType triggerItem) {

		if (triggerItem.equals(MenuItemType.MENU_ITEM)) {
			getView().inactivateSingleLinks();
		} else {
			getView().closeCollapisbles();
		}

	}

	@Override
	public void logout() {
		OauthUtils.removeAccessToken();
		placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.LOGIN).build());
	}

	@Override
	public void referesh() {
		if (appData.getName() != null) {
			getView().setAppFullname(appData.getName());
		}

		if (currentUser.getAccountDto() != null) {
			getView().setUserImageUrl(currentUser.getAccountDto().getImageUrl(),
					currentUser.getAccountDto().getNickname());
			getView().setUserName(currentUser.getAccountDto().getNickname());
		}
	}

	public void setProfileBackground(ImageResource resource) {
		getView().setProfileBackground(resource);
	}
}