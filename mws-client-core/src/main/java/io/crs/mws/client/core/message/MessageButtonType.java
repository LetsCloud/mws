/**
 * 
 */
package io.crs.mws.client.core.message;

/**
 * @author robi
 *
 */
public enum MessageButtonType {
	CLOSE("DB_CLOSE"), YES("DB_YES"), NO("DB_NO");

	private String label;

	private MessageButtonType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
