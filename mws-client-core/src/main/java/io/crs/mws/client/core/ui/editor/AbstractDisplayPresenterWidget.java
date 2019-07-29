/**
 * 
 */
package io.crs.mws.client.core.ui.editor;

import java.util.logging.Logger;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author robi
 *
 */
public abstract class AbstractDisplayPresenterWidget<V extends View> extends PresenterWidget<V> {
	private static Logger logger = Logger.getLogger(AbstractDisplayPresenterWidget.class.getName());

	private Boolean readOnly = true;

	private String webSafeKey;

	public AbstractDisplayPresenterWidget(EventBus eventBus, V view) {
		super(eventBus, view);
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getWebSafeKey() {
		logger.info("AbstractEditorPresenterWidget().getWebSafeKey()->webSafeKey=" + webSafeKey);
		return webSafeKey;
	}

	public void setWebSafeKey(String webSafeKey) {
		logger.info("AbstractEditorPresenterWidget().setWebSafeKey(" + webSafeKey + ")");
		this.webSafeKey = webSafeKey;
	}

}
