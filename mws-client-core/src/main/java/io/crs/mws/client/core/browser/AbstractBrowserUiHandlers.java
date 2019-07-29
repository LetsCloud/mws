/**
 * 
 */
package io.crs.mws.client.core.browser;

import java.util.List;

import com.gwtplatform.mvp.client.UiHandlers;

import io.crs.mws.shared.dto.BaseDto;

/**
 * A {@link AbstractBrowserPresenter} által implementált, CRUD funkciókat
 * végrehajtó helyeket meghívó metódusokat definiáló interfész.
 * 
 * @author robi
 *
 */
public interface AbstractBrowserUiHandlers<T extends BaseDto> extends UiHandlers {

	/**
	 * Új adat objektum létrehozása metódus, amelyben meg kell hívni a létrehozást
	 * végző helyet.
	 */
	void addNew();

	/**
	 * Meglévő adat objektum szerkesztése metódus, amelyben meg kell hívni a
	 * szerkesztést végző helyet.
	 * 
	 * @param dto A szerkesztendő adat objektum.
	 */
	void edit(T dto);

	/**
	 * A kiválasztott adat objektumok törlése metódus, amelyben meg kell hívni
	 * törlést végző helyet.
	 * 
	 * @param dtos A törlendő adat objektumok listája.
	 */
	void delete(List<T> dtos);

}
