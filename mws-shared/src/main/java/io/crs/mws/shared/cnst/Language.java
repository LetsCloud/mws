/**
 * 
 */
package io.crs.mws.shared.cnst;

import java.io.Serializable;

/**
 * @author robi
 *
 */
public enum Language implements Serializable {
	en("LNG_EN"), hu("LNG_HU"), uk("LNG_UK");

	private final String text;

	/**
	 * @param text
	 */
	Language(final String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
