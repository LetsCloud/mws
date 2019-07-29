/**
 * 
 */
package io.crs.mws.server.model;

import java.io.Serializable;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class Subscription implements Serializable {

	private String iidToken;

	public Subscription() {
	}

	public Subscription(String iidToken) {
		this();
		this.iidToken = iidToken;
	}

	public String getIidToken() {
		return iidToken;
	}

	public void setIidToken(String iidToken) {
		this.iidToken = iidToken;
	}
}
