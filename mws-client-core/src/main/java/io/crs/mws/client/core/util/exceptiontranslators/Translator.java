/**
 * 
 */
package io.crs.mws.client.core.util.exceptiontranslators;

/**
 * @author CR
 *
 */
public interface Translator {
	Boolean isMatching();

	String getTranslatedMessage();
}