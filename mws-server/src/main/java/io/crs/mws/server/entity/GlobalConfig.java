/**
 * 
 */
package io.crs.mws.server.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import io.crs.mws.shared.cnst.GlobalParam;

/**
 * @author robi
 *
 */
@Entity
public class GlobalConfig extends BaseEntity {
	private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class.getName());

	/**
	 * Key
	 */
	@Index
	private GlobalParam code;

	/**
	 * Value
	 */
	private String value;

	/**
	 * Objectify miatt
	 */
	public GlobalConfig() {
		logger.debug("GlobalConfig()");
	}

	public GlobalConfig(GlobalParam key) {
		this();
		this.code = key;
	}

	public GlobalParam getCode() {
		return code;
	}

	public void setCode(GlobalParam code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "GlobalConfig [code=" + code + ", value=" + value + "]";
	}

}
