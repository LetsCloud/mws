/**
 * 
 */
package io.crs.mws.client.core.ui;

import gwt.material.design.client.ui.MaterialLink;

/**
 * @author CR
 *
 */
public class MaterialLinkPermitted extends MaterialLink {

	private String permission;

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
