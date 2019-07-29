/**
 * 
 */
package io.crs.mws.client.adm.config.system;

import javax.inject.Inject;

import io.crs.mws.client.core.config.AbstractConfigView;

/**
 * @author robi
 *
 */
public class SystemConfigView extends AbstractConfigView implements SystemConfigPresenter.MyView {

	@Inject
	SystemConfigView() {
		super();
	}
}
