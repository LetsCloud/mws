/**
 * 
 */
package io.crs.mws.client.core.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * @author robi
 *
 */
public interface GssResources extends ClientBundle {
	public static final GssResources INSTANCE = GWT.create(GssResources.class);

	interface Style extends CssResource {
		String chat_list_view_col_left();
		String chat_list_view_col_right();
		String chat_list_view_collection();
		
		String chat_details_widget();

		String chat_creator_panel();

		String send_message_textbox();
	}

	@Source({"io/crs/mws/client/core/resources/css/common.gss" })
	Style common();
}
