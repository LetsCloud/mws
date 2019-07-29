/**
 * 
 */
package io.crs.mws.client.core.filter.accountchild;

import java.util.logging.Logger;

import javax.inject.Inject;

import io.crs.mws.client.core.i18n.CoreConstants;
import io.crs.mws.client.core.i18n.CoreMessages;
import io.crs.mws.client.core.ui.filter.AbstractFilterView;

/**
 * @author robi
 *
 */
public class AccountChildFilterView extends AbstractFilterView implements AccountChildFilterPresenter.MyView {
	private static Logger logger = Logger.getLogger(AccountChildFilterView.class.getName());

	@Inject
	AccountChildFilterView(CoreMessages i18nCore, CoreConstants cnstCore) {
		super(i18nCore);
		logger.info("AccountChildFilterView()");
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
}
