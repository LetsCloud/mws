/**
 * 
 */
package io.crs.mws.client.core.event;

import java.util.logging.Logger;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

import io.crs.mws.client.core.model.BreadcrumbConfig;

/**
 * @author robi
 *
 */
public class SetBreadcrumbsEvent extends GwtEvent<SetBreadcrumbsEvent.SetBreadcrumbsHandler> {
	private static Logger logger = Logger.getLogger(SetBreadcrumbsEvent.class.getName());

	public interface SetBreadcrumbsHandler extends EventHandler {
		void onSetBreadcrumbs(SetBreadcrumbsEvent event);
	}

	public static final Type<SetBreadcrumbsHandler> TYPE = new Type<>();

	private final BreadcrumbConfig breadcrumbData;

	public SetBreadcrumbsEvent(BreadcrumbConfig breadcrumbData) {
		logger.info("SetBreadcrumbsEvent()");
		this.breadcrumbData = breadcrumbData;
	}

	public static void fire(BreadcrumbConfig breadcrumbData, HasHandlers source) {
		logger.info("SetBreadcrumbsEvent().fire()");
		source.fireEvent(new SetBreadcrumbsEvent(breadcrumbData));
	}

	public BreadcrumbConfig getBreadcrumbData() {
		return breadcrumbData;
	}

	@Override
	public Type<SetBreadcrumbsHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SetBreadcrumbsHandler handler) {
		handler.onSetBreadcrumbs(this);
	}
}