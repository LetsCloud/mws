/**
 * 
 */
package io.crs.mws.client.core.browser;

import static io.crs.mws.shared.api.ApiParameters.WEBSAFEKEY;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

import io.crs.mws.client.core.editor.AbstractDisplayPresenterWidget;
import io.crs.mws.client.core.event.RefreshTableEvent;
import io.crs.mws.client.core.event.RefreshTableEvent.TableType;
import io.crs.mws.client.core.model.BreadcrumbConfig;
import io.crs.mws.shared.dto.BaseDto;

/**
 * Az <b>AbstractBrowserPresenter</b> egy <b>PresenterWidget</b> utód, amely
 * leszármaztatásakor paraméterként meg kell adni a megjelenítendő adat
 * <b>BasBaseDto</b> utodú típusát és a <b>View</b> utódú megjelenítő nézetet.
 * <p>
 * Az osztály a megjelenítő widget megjelenítésekor (onReveal) a
 * {@link #loadData()} metódus segítségével betölti a megjelenítendő adatokat az
 * utódban kifejtett módon.
 * <p>
 * Az osztály meghívja a nézeten keresztül a felhasználó által kezdeményezet
 * alábbi CRUD funkciókat:
 * <ul>
 * <li>Új adat létrehozása.
 * <li>A kiválasztott adat módosítása.
 * <li>A kiválasztott adat törlése.
 * </ul>
 * 
 * @author robi
 *
 */
public abstract class AbstractBrowserPresenter<T extends BaseDto, V extends View>
		extends AbstractDisplayPresenterWidget<V>
		implements AbstractBrowserUiHandlers<T>, RefreshTableEvent.RefreshTableHandler {
	private static Logger logger = Logger.getLogger(AbstractBrowserPresenter.class.getName());

	/**
	 * A megjelenítő komponens felirat.
	 */
	private String caption;
	private BreadcrumbConfig breadcrumbConfig;
	private Map<String, String> filters = new HashMap<String, String>();
	private final PlaceManager placeManager;

	public AbstractBrowserPresenter(EventBus eventBus, V view, PlaceManager placeManager) {
		super(eventBus, view);
		logger.info("AbstractBrowserPresenter()");

		this.placeManager = placeManager;

		addRegisteredHandler(RefreshTableEvent.TYPE, this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		loadData();
	}

	/**
	 * A widget mejelenítésekor végrehajtandó adatbetöltés, ami az utóban fejtendő
	 * ki.
	 */
	protected abstract void loadData();

	public void refresh() {
		loadData();
	}

	/**
	 * A törzsadatmegjelenítő feliratának kiolvasása.
	 * 
	 * @return A törzsadatmegjelenítő felirata.
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * A törzsadatmegjelenítő feliratának megadása.
	 * 
	 * @param caption A törzsadatmegjelenítő felirata.
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Az utódban kifejtendő eljárás, amely a törzsadat létrehozó oldal névjelzőjét
	 * adja vissza.
	 * 
	 * @return A törzsadatszerkeztő oldal névjelzője.
	 */
	protected abstract String getCreatorNameToken();

	/**
	 * Az utódban kifejtendő eljárás, amely a törzsadatszerkesztő oldal névjelzőjét
	 * adja vissza.
	 * 
	 * @return A törzsadatszerkeztő oldal névjelzője.
	 */
	protected abstract String getEditorNameToken();

	@Override
	public void addNew() {
		logger.info("AbstractBrowserPresenter().addNew()");
		Builder placeBuilder = new Builder().nameToken(getCreatorNameToken());
		placeBuilder = configCreatePlaceBuilder(placeBuilder);
		placeManager.revealPlace(addFilters(placeBuilder));
	}

	protected Builder configCreatePlaceBuilder(Builder builder) {
		return builder;
	}

	@Override
	public void edit(T dto) {
		logger.info("AbstractBrowserPresenter().edit(" + dto + ")");
		Builder placeBuilder = new Builder().nameToken(getEditorNameToken());
		placeBuilder.with(WEBSAFEKEY, String.valueOf(dto.getWebSafeKey()));
		placeBuilder = configEditPlaceBuilder(placeBuilder, dto);
		placeManager.revealPlace(addFilters(placeBuilder));
	}

	protected Builder configEditPlaceBuilder(Builder builder, T dto) {
		return builder;
	}

	private PlaceRequest addFilters(Builder placeBuilder) {
		logger.info("AbstractBrowserPresenter().addFilters()");
		if (!filters.isEmpty()) {
			logger.info("AbstractBrowserPresenter().addFilters()-2");
			placeBuilder.with(filters);
		}
		logger.info("AbstractBrowserPresenter().addFilters()-3");
		return placeBuilder.build();
	}

	protected abstract TableType getTableType();

	@Override
	public void onRefresh(RefreshTableEvent event) {
		if (event.getTableType().equals(getTableType()))
			loadData();
	}

	@Override
	public void delete(List<T> dtos) {
		logger.info("AbstractBrowserPresenter().delete()");
		for (T dto : dtos) {
			logger.info("AbstractBrowserPresenter().delete()->dto.getWebSafeKey()=" + dto.getWebSafeKey());
			deleteData(dto.getWebSafeKey());
		}
	}

	protected abstract void deleteData(String webSafeKey);

	protected void addFilter(String key, String value) {
		filters.put(key, value);
	}

	protected void setFilter(String key, String value) {
		if (filters.containsKey(key)) {
			filters.replace(key, value);
			return;
		}
		addFilter(key, value);
	}

	protected String getFilter(String key) {
		return filters.get(key);
	}

	public BreadcrumbConfig getBreadcrumbConfig() {
		return breadcrumbConfig;
	}

	public void setBreadcrumbConfig(BreadcrumbConfig breadcrumbConfig) {
		this.breadcrumbConfig = breadcrumbConfig;
	}
	
}
