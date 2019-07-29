/**
 * 
 */
package io.crs.mws.client.core.filter;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import io.crs.mws.client.core.filter.accountchild.AccountChildFilterPresenter;
import io.crs.mws.client.core.filter.accountchild.AccountChildFilterView;
import io.crs.mws.client.core.table.filter.FilterWidgetPresenter;
import io.crs.mws.client.core.table.filter.FilterWidgetView;

/**
 * @author robi
 *
 */
public class FilterModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenterWidget(FilterWidgetPresenter.class, FilterWidgetPresenter.MyView.class, FilterWidgetView.class);

		bindPresenterWidget(AccountChildFilterPresenter.class, AccountChildFilterPresenter.MyView.class,
				AccountChildFilterView.class);

		install(new GinFactoryModuleBuilder().build(FilterPresenterFactory.class));
	}
}
