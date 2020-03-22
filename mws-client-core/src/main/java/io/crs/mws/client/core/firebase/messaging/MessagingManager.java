/**
 * 
 */
package io.crs.mws.client.core.firebase.messaging;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;

import gwt.material.design.client.pwa.serviceworker.js.ServiceWorkerRegistration;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import io.crs.mws.client.core.firebase.Config;
import io.crs.mws.client.core.firebase.Firebase;
import io.crs.mws.client.core.firebase.messaging.js.Fnx;
import io.crs.mws.client.core.firebase.messaging.js.Messaging;
import io.crs.mws.client.core.promise.xgwt.Fn;
import io.crs.mws.client.core.security.AppData;
import io.crs.mws.client.core.security.CurrentUser;
import io.crs.mws.client.core.service.FcmService;
import io.crs.mws.client.core.service.GlobalConfigService;
import io.crs.mws.client.core.util.Base64Utils;
import io.crs.mws.shared.cnst.GlobalParam;
import io.crs.mws.shared.dto.GlobalConfigDto;

/**
 * @author robi
 *
 */
public class MessagingManager implements HasMessagingFeatures {
	private static Logger logger = Logger.getLogger(MessagingManager.class.getName());

	private static final GlobalConfigService GLOBALCONFIG_SERVICE = GWT.create(GlobalConfigService.class);
	private static final FcmService FCM_SERVICE = GWT.create(FcmService.class);

	private Firebase firebase;
	private Boolean registered;
	private boolean firebaseIsReady = false;
	private ServiceWorkerRegistration registration;

	Fnx.NoArg unsubscribe;

	private final AppData appData;
	private final CurrentUser currentUser;

	@Inject
	MessagingManager(EventBus eventBus, AppData appData, CurrentUser currentUser) {
		logger.info("MessagingManager()");
		this.appData = appData;
		this.currentUser = currentUser;
		registered = false;
	}

	public Firebase getFirebase() {
		logger.info("MessagingManager.getFirebase()");
		return firebase;
	}

	public void setFirebase(Firebase firebase) {
		logger.info("MessagingManager.setFirebase()");
		this.firebase = firebase;
		registered = true;
	}

	public Boolean isRegistered() {
		logger.info("MessagingManager.isRegistered()=" + registered);
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	@Override
	public Messaging getFirebaseMessaging() {
		logger.info("MessagingManager.getFirebaseMessaging()");
		if (firebase != null) {
			return firebase.messaging();
		} else {
			GWT.log("Firebase is not yet registered", new IllegalStateException());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getToken(Fn.Arg<String> callback) {
		logger.info("MessagingManager.getToken()");
		getFirebaseMessaging().getToken().then(object -> {
			String token = (String) object;
			logger.info("getToken()->token=" + token);
			callback.call(token);
		}).katch(error -> {
			logger.info("getToken().katch()->" + error.toString());
		});
	}

	@Override
	public void useServiceWorker(ServiceWorkerRegistration serviceWorker) {
		logger.info("MessagingManager.useServiceWorker()");
		this.registration = serviceWorker;
//		getFirebaseMessaging().useServiceWorker(serviceWorker);
	}

	@Override
	public void requestPermission(Fn.NoArg callback) {
		logger.info("MessagingManager.requestPermission()");
		getFirebaseMessaging().requestPermission().then(() -> {
			callback.call();
		});
	}

	@Override
	public void onTokenRefresh(Fn.Arg<String> callback) {
		logger.info("MessagingManager.onTokenRefresh()");
		getFirebaseMessaging().onTokenRefresh(() -> {
			getToken(callback);
		});
	}

	@Override
	public void onMessage(Fnx.Arg callback) {
		logger.info("MessagingManager.onMessage()");
		unsubscribe = getFirebaseMessaging().onMessage(callback);
	}

	public void initFirebase(String webSafeKey, Fn.NoArg callback) {
		logger.info("MessagingManager.initFirebase()");

		if (getFirebase() != null) {
			if (currentUser.isLoggedIn())
				subscribe(currentUser.getAccountDto().getWebSafeKey());

			callback.call();
			return;
		}

		GLOBALCONFIG_SERVICE.getAll(new MethodCallback<List<GlobalConfigDto>>() {

			@Override
			public void onSuccess(Method method, List<GlobalConfigDto> response) {
				logger.info("MessagingManager.initFirebase().onSuccess()");

				Config config = new Config();
				config.setApiKey(getGlobalSetting(response, GlobalParam.FB1_API_KEY.name()));
				config.setAuthDomain(getGlobalSetting(response, GlobalParam.FB2_AUTH_DOMAIN.name()));
				config.setDatabaseURL(getGlobalSetting(response, GlobalParam.FB3_DATABASE_URL.name()));
				config.setProjectId(getGlobalSetting(response, GlobalParam.FB4_PROJECT_ID.name()));
				config.setStorageBucket(getGlobalSetting(response, GlobalParam.FB5_STORAGE_BUCKET.name()));
				config.setMessagingSenderId(getGlobalSetting(response, GlobalParam.FB6_MESSAGE_SENDER_ID.name()));
				config.setAppId(getGlobalSetting(response, GlobalParam.FB7_APP_ID.name()));

				setFirebase(Firebase.initializeApp(config));
				logger.info("MessagingManager.initFirebase().onSuccess()->firebase.getName()" + firebase.getName());

				if (registration != null)
					getFirebaseMessaging().useServiceWorker(registration);

				configFcmOnMessage();

				if (currentUser.isLoggedIn())
					subscribe(currentUser.getAccountDto().getWebSafeKey());

				callback.call();
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				logger.info("MessagingManager.initFirebase().onFailure()");
//				checkCurrentUser();
			}
		});
	}

	private String getGlobalSetting(List<GlobalConfigDto> result, String key) {
		logger.info("MessagingManager.getGlobalSetting()");
		return result.stream().filter(o -> o.getCode().equals(key)).findFirst().get().getValue();
	}

	private void configFcmOnMessage() {
		logger.info("MessagingManager.configOnFcmMessage()");
		onMessage(fcmMessage -> {
			logger.info("configOnFcmMessage()->dataMessage.getData().getClick_action()="
					+ fcmMessage.getData().getAction());

			String action = fcmMessage.getData().getAction();
			logger.info("configOnFcmMessage()->action=" + action);

			String href2 = Window.Location.getHref();
			logger.info("configOnFcmMessage()->href2=" + href2);

			MaterialLink link = new MaterialLink();
			if (action.equals(href2)) {
				link.setText("FRISSÍTEM");
				link.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						logger.info("configFcmOnMessage()->refresh.onClick()");
						Window.Location.reload();
					}
				});
			} else {
				link.setText("MEGNYITOM");
				String href = action.substring(action.indexOf("#"));
				logger.info("configOnFcmMessage()->href=" + href);
				link.setHref(href);
			}
			new MaterialToast(link)
					.toast("ÜZENET:" + fcmMessage.getData().getTitle() + "->" + fcmMessage.getData().getBody(), 10000);
		});
	}

	private String getManifest() {
		logger.info("MessagingManager.getGlobalSetting()");
		return appData.getManifest();
	}

	public void subscribe(String webSafeKey) {
		logger.info("MessagingManager.getGlobalSetting()");
		requestPermission(() -> getToken(token -> {
			fcmSubscribe(webSafeKey, token);
		}));
	}

	/**
	 * 
	 * @param iidToken
	 */
	private void fcmSubscribe(String webSafeKey, String iidToken) {
		logger.info("MessagingManager.fcmSubscribe()->iidToken=" + iidToken);
		String userAgent = Base64Utils.toBase64(getUserAgent().getBytes());
		logger.info("MessagingManager.fcmSubscribe()->userAgent=" + userAgent);

		FCM_SERVICE.subscribe(webSafeKey, iidToken, userAgent, new MethodCallback<Void>() {

			@Override
			public void onSuccess(Method method, Void response) {
				MaterialToast.fireToast("Sussecfull subscription!");
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				MaterialToast.fireToast(exception.getMessage());
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	public static native String getUserAgent() /*-{
												return $wnd.navigator.userAgent.toLowerCase();
												}-*/;
}
