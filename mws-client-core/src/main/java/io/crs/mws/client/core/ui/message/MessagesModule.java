/**
 * 
 */
package io.crs.mws.client.core.ui.message;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.core.ui.message.ui.MessageWidgetFactory;


/**
 * @author CR
 *
 */
public class MessagesModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(MessageWidgetFactory.class));

        bindSingletonPresenterWidget(MessagesPresenter.class, MessagesPresenter.MyView.class, MessagesView.class);
    }
}
