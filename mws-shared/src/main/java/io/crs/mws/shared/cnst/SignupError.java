/**
 * 
 */
package io.crs.mws.shared.cnst;

/**
 * @author robi
 *
 */
public enum SignupError {
	successful("SUE_SUCCESS"), invalidRecaptha("SUE_INVALID_RECAPTCHA"), failedRecaptcha("SUE_FAILED_RECAPTCHA"),
	existingEmail("SUE_EXISTING_EMAIL"), invalidEntity("SUE_INVALID_ENTITY"),
	indexColossion("SUE_INDEX_COLOSSION");

	private final String text;

	/**
	 * @param text
	 */
	SignupError(final String text) {
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