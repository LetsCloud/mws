/**
 * 
 */
package io.crs.mws.client.core.menu;

import com.gwtplatform.mvp.client.UiHandlers;

import io.crs.mws.client.core.event.ContentPushEvent.MenuState;

/**
 * @author CR
 *
 */
public interface MenuUiHandlers extends UiHandlers {

	void setContentPush(MenuState menuState);
	
	void logout();
	
	void referesh();
}