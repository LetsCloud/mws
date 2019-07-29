/**
 * 
 */
package io.crs.mws.client.adm.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * @author CR
 *
 */
public interface AdminMessages extends Messages {

	/*
	 * MAIN MENU
	 */

	@DefaultMessage("Dashboard")
	String mainMenuItemDashboard();

	
	// Coommon	
	@DefaultMessage("Configurations")
	String mainSubMenuConfigs();
	
	@DefaultMessage("System Configs")
	String menuItemSystemConfigs();

	
	/*
	 * SYSTEM CONFIG
	 */

	@DefaultMessage("System Configs")
	String systemConfigTitle();

	@DefaultMessage("All system configuration...")
	String systemConfigDescription();

	
	/*
	 * GLOBAL CONFIG BROWSER
	 */

	@DefaultMessage("Global Configs")
	String globalConfigBrowserTitle();

	@DefaultMessage("Code")
	String globalConfigBrowserFieldCode();

	@DefaultMessage("Value")
	String globalConfigBrowserFieldValue();

	
	/*
	 * GLOBAL CONFIG EDITOR
	 */

	@DefaultMessage("Create Global Config")
	String globalConfigCreatorTitle();

	@DefaultMessage("Edit Global Config")
	String globalConfigEditorTitle();

	@DefaultMessage("Code")
	String globalConfigEditorFieldCode();

	@DefaultMessage("Value")
	String globalConfigEditorFieldValue();
}
