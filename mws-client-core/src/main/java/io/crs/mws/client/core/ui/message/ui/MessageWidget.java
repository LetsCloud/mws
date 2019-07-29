/**
 * 
 */
package io.crs.mws.client.core.ui.message.ui;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
// import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import io.crs.mws.client.core.message.MessageCloseDelay;
import io.crs.mws.client.core.message.MessageData;

/**
 * @author CR
 *
 */
public class MessageWidget extends Composite {
    interface Binder extends UiBinder<Widget, MessageWidget> {
    }

    @UiField
    HTMLPanel messageBox;
    @UiField
    InlineHTML close;
//    @UiField(provided = true)
//    AppResources appResources;
    @UiField
    InlineLabel messageLabel;

    private final MessageData message;
    private final Timer closeTimer = new Timer() {
        @Override
        public void run() {
            close();
        }
    };

    @Inject
    MessageWidget(
            Binder binder,
  //          AppResources appResources,
            @Assisted MessageData message) {
 //       this.appResources = appResources;
        this.message = message;

        initWidget(binder.createAndBindUi(this));
        initContent();
        initTimeout();
    }

    @Override
    protected void onLoad() {
 //       $(messageBox).fadeIn();
    }

    @SuppressWarnings("unused")
    @UiHandler("close")
    void onCloseAnchorClicked(ClickEvent event) {
        close();
    }

    private void close() {
  /*  	
        $(messageBox).fadeOut(new Function() {
            @Override
            public void f() {
                MessageWidget.this.removeFromParent();
            }
        }); */
    }

    private void initContent() {
        messageLabel.setText(message.getDescription());

        switch (message.getStyle()) {
            case SUCCESS:
//                messageBox.addStyleName(appResources.styles().success());
                HTMLPanel.ensureDebugId(messageBox.getElement(), "successMessage");
                break;
            case ERROR:
//                messageBox.addStyleName(appResources.styles().error());
                HTMLPanel.ensureDebugId(messageBox.getElement(), "errorMessage");
                break;
        }
    }

    private void initTimeout() {
        if (!message.getCloseDelay().equals(MessageCloseDelay.NEVER)) {
            closeTimer.schedule(message.getCloseDelay().getDelay());
        }
    }
}
