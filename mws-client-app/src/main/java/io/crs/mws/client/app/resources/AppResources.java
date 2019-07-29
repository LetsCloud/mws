/**
 * 
 */
package io.crs.mws.client.app.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author CR
 *
 */
public interface AppResources extends ClientBundle {
	public static final AppResources INSTANCE = GWT.create(AppResources.class);

	@Source("img/grey-blue-wallpaper-3.jpg")
	ImageResource profileBackgroundImg();
}