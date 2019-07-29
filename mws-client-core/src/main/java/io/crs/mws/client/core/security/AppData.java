/**
 * 
 */
package io.crs.mws.client.core.security;

/**
 * @author CR
 *
 */
public class AppData {

	private String appCode;
	private String name;

	public AppData() {
		this.name = "MyWindSpot <span>v1.0</span>";
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getManifest() {
		return appCode + "_manifest.json";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
