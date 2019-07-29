/**
 * 
 */
package io.crs.mws.client.core.message.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author CR
 *
 */
public abstract class AbstractAsyncCallback<T> implements AsyncCallback<T> {
	@Override
	public void onFailure(Throwable caught) {
	}
}
