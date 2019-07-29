/**
 * 
 */
package io.crs.mws.server.service;

import java.util.List;

import io.crs.mws.server.entity.GlobalConfig;

/**
 * @author robi
 *
 */
public interface GlobalConfigService extends CrudService<GlobalConfig> {

	public void checkParams();

	public List<GlobalConfig> getParams();

}
