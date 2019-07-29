/**
 * 
 */
package io.crs.mws.client.core.firebase.messaging.js;

import io.crs.mws.client.core.firebase.model.FcmMessageJs;
import jsinterop.annotations.JsFunction;

/**
 * @author robi
 *
 */
public class Fnx {
	
	@FunctionalInterface
	@JsFunction
	public interface Arg {
		void apply(FcmMessageJs val);
	}

	@FunctionalInterface
	@JsFunction
	public interface NoArg {
		void apply();
	}
}
