package io.crs.mws.client.core.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.presenter.slots.NestedSlot;
import com.gwtplatform.mvp.client.presenter.slots.PermanentSlot;
import com.gwtplatform.mvp.client.presenter.slots.SingleSlot;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;

import io.crs.mws.client.core.app.AbstractAppPresenter.MyView;
import io.crs.mws.client.core.event.SetBreadcrumbsEvent;
import io.crs.mws.client.core.event.SetPageTitleEvent;
import io.crs.mws.client.core.event.SetBreadcrumbsEvent.SetBreadcrumbsHandler;
import io.crs.mws.client.core.event.SetPageTitleEvent.SetPageTitleHandler;
import io.crs.mws.client.core.firebase.messaging.MessagingManager;
import io.crs.mws.client.core.menu.MenuPresenter;
import io.crs.mws.client.core.model.BreadcrumbConfig;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.service.AuthService;

public abstract class AbstractAppPresenter<Proxy_ extends Proxy<?>> extends Presenter<MyView, Proxy_>
		implements NavigationHandler, SetPageTitleHandler, SetBreadcrumbsHandler {
	private static Logger logger = Logger.getLogger(AbstractAppPresenter.class.getName());

	private static final AuthService AUTH_SERVICE = GWT.create(AuthService.class);

	public interface MyView extends View {
		void setPageTitle(String title, String description);

		void setBreadcrumbs(List<BreadcrumbConfig> breadcrumbConfigs);

		void displayUserName(String userName);
	}

	public static final PermanentSlot<MenuPresenter> SLOT_MENU = new PermanentSlot<>();
	public static final NestedSlot SLOT_MAIN = new NestedSlot();
	public static final SingleSlot<PresenterWidget<?>> SLOT_MODAL = new SingleSlot<>();

	private final MenuPresenter menuPresenter;
	private final AppServiceWorkerManager swManager;

	protected List<BreadcrumbConfig> breadcrumbConfigs = new ArrayList<BreadcrumbConfig>();

	protected AbstractAppPresenter(EventBus eventBus, MyView view, Proxy_ proxy, PlaceManager placeManager,
			MenuPresenter menuPresenter, CurrentUser currentUser, String appCode, AppServiceWorkerManager swManager,
			MessagingManager messagingManager) {
		super(eventBus, view, proxy, RevealType.Root);
		logger.info("AbstractAppPresenter()");

		this.menuPresenter = menuPresenter;
		this.swManager = swManager;
	}

	@Override
	protected void onBind() {
		super.onBind();
		setInSlot(SLOT_MENU, menuPresenter);

		addRegisteredHandler(NavigationEvent.getType(), this);
		addRegisteredHandler(SetPageTitleEvent.TYPE, this);
		addRegisteredHandler(SetBreadcrumbsEvent.TYPE, this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		logger.info("AbstractAppPresenter.onReveal()");
		menuPresenter.referesh();
		@SuppressWarnings("deprecation")
		com.google.gwt.user.client.Element splash = DOM.getElementById("splashscreen");
		if (splash != null)
			splash.removeFromParent();
	}

	public void logout() {
		AUTH_SERVICE.logout(new MethodCallback<Void>() {

			@Override
			public void onSuccess(Method method, Void response) {
//				PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(CoreNameTokens.getLogin()).build();
//				currentUser.setLoggedIn(false);
//				placeManager.revealPlace(placeRequest);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
			}
		});
	}

	@Override
	public void onNavigation(NavigationEvent navigationEvent) {
		Window.scrollTo(0, 0);
	}

	@Override
	public void onSetPageTitle(SetPageTitleEvent event) {
		getView().setPageTitle(event.getTitle(), event.getDescription());
//cr		menuPresenter.adjustMenuItems(event.getMenuItemType());
	}

	@Override
	public void onSetBreadcrumbs(SetBreadcrumbsEvent event) {
		logger.info("AbstractAppPresenter().onSetBreadcrumbs()->breadcrumbConfigs.size()=" + breadcrumbConfigs.size());
		BreadcrumbConfig data = event.getBreadcrumbData();

		Iterator<BreadcrumbConfig> i = breadcrumbConfigs.iterator();
		while (i.hasNext()) {
			BreadcrumbConfig s = i.next(); // must be called before you can call i.remove()
			if (s.getLevel() >= data.getLevel())
				i.remove();
		}
		/*
		 * for (int i = data.getLevel(); i < breadcrumbConfigs.size(); i++) {
		 * logger.info("AbstractAppPresenter().onSetBreadcrumbs()->i=" + i);
		 * breadcrumbConfigs.remove(i); }
		 */
		breadcrumbConfigs.add(data);

		getView().setBreadcrumbs(breadcrumbConfigs);
	}

	public MenuPresenter getMenuPresenter() {
		return menuPresenter;
	}

	public AppServiceWorkerManager getServiceWorkerManager() {
		return swManager;
	}
}
