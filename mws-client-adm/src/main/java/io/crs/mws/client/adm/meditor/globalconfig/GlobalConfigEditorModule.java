/**
 * 
 */
package io.crs.mws.client.adm.meditor.globalconfig;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author robi
 *
 */
public class GlobalConfigEditorModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenterWidget(GlobalConfigEditorPresenter.class, GlobalConfigEditorPresenter.MyView.class,
				GlobalConfigEditorView.class);

		install(new GinFactoryModuleBuilder().build(GlobalConfigEditorFactory.class));
	}
}
