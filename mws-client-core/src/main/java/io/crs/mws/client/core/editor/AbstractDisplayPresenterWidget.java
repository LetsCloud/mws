/**
 * 
 */
package io.crs.mws.client.core.editor;

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

	private Integer index;
	private String webSafeKey;
	private String title;
	private String description;

	public AbstractDisplayPresenterWidget(EventBus eventBus, V view) {
		super(eventBus, view);
		logger.info("AbstractDisplayPresenterWidget()");
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getWebSafeKey() {
		return webSafeKey;
	}

	public void setWebSafeKey(String webSafeKey) {
		this.webSafeKey = webSafeKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
