/**
 * 
 */
package io.crs.mws.client.core.ui.message;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import io.crs.mws.client.core.event.DisplayMessageEvent;
import io.crs.mws.client.core.message.MessageData;

/**
 * @author CR
 *
 */
public class MessagesPresenter extends PresenterWidget<MessagesPresenter.MyView>
		implements DisplayMessageEvent.DisplayMessageHandler {
	
	interface MyView extends View {
		void addMessage(MessageData message);
	}

	@Inject
	MessagesPresenter(EventBus eventBus, MyView view) {
		super(eventBus, view);
	}

	@Override
	public void onDisplayMessage(DisplayMessageEvent event) {
		getView().addMessage(event.getMessage());
	}

	@Override
	protected void onBind() {
		addRegisteredHandler(DisplayMessageEvent.getType(), this);
	}
}
