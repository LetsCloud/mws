/**
 * 
 */
package io.crs.mws.client.core.filter;

import io.crs.mws.client.core.filter.accountchild.AccountChildFilterPresenter;
import io.crs.mws.client.core.table.filter.FilterWidgetPresenter;

/**
 * @author robi
 *
 */
public interface FilterPresenterFactory {

	AccountChildFilterPresenter createAccountChildFilter();

	FilterWidgetPresenter createFilterWidgetPresenter();
}
