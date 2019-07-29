/**
 * 
 */
package io.crs.mws.client.core.message.dialog;

import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialTitle;
import io.crs.mws.client.core.i18n.CoreConstants;
import io.crs.mws.client.core.message.MessageButtonConfig;
import io.crs.mws.client.core.message.MessageButtonType;
import io.crs.mws.client.core.message.MessageData;
import io.crs.mws.client.core.promise.xgwt.Fn;

/**
 * @author robi
 *
 */
public class MessageDialogWidget extends Composite implements HasShowMessage {
	private static Logger logger = Logger.getLogger(MessageDialogWidget.class.getName());

	private static MessageDialogWidgetUiBinder uiBinder = GWT.create(MessageDialogWidgetUiBinder.class);

	interface MessageDialogWidgetUiBinder extends UiBinder<Widget, MessageDialogWidget> {
	}

	private Map<String, String> dialogButtonMap;

	@UiField
	MaterialDialog dialog;

	@UiField
	MaterialTitle title;

	@UiField
	MaterialDialogFooter footer;

	/**
	 * 
	 */
	public MessageDialogWidget() {
		logger.info("MessageDialogWidget()");
		initWidget(uiBinder.createAndBindUi(this));
		CoreConstants coreConstants = GWT.create(CoreConstants.class);
		dialogButtonMap = coreConstants.dialogButtonMap();
	}

	private MaterialButton createCloseButton() {
		MaterialButton b = new MaterialButton(dialogButtonMap.get(MessageButtonType.CLOSE.getLabel()));
		b.setMarginRight(10);
		b.addClickHandler(e -> dialog.close());
		return b;
	}

	private MaterialButton createButton(String label, Fn.Arg<ClickEvent> callback) {

		MaterialButton b = new MaterialButton(label);
		b.setMarginRight(10);
		b.addClickHandler(e -> {
			dialog.close();
			Timer timer = new Timer() {
				public void run() {
					callback.call(e);
				}
			};
			timer.schedule(500);
		});
		return b;
	}

	@Override
	public void showMessage(MessageData messageData) {
		logger.info("MessageDialogWidget().showMessage()");
		initAndOpen(messageData);
	}

	private void initAndOpen(MessageData messageData) {
		logger.info("MessageDialogWidget().initAndOpen()");
		title.setTitle(messageData.getTitle());
		title.setDescription(messageData.getDescription());
		footer.clear();
		if (messageData.getBttonConfigs().isEmpty()) {
			footer.add(createCloseButton());
		} else {
			for (MessageButtonConfig buttonConfig : messageData.getBttonConfigs()) {
				footer.add(createButton(dialogButtonMap.get(buttonConfig.getType().getLabel()),
						buttonConfig.getCallback()));
			}
		}
		dialog.open();
	}

}
