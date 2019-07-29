/**
 * 
 */
package io.crs.mws.client.core.firebase;

/**
 * @author robi
 *
 */
public interface HandlerRegistration {
	<R extends HandlerRegistration> R on();

	<R extends HandlerRegistration> R off();
}
