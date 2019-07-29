/**
 * 
 */
package io.crs.mws.client.core.promise.xgwt;

import jsinterop.annotations.JsFunction;

/**
 * @author robi
 *
 */
public abstract class Fn {
	private Fn() {
	}

	@JsFunction
	public interface NoArg {
		void call();
	}

	@JsFunction
	public interface Arg<A> {
		void call(A arg);
	}

	@JsFunction
	public interface ArgRet<A, R> {
		R call(A arg);
	}

	@JsFunction
	public interface Args {
		void call(Object... args);
	}

	@JsFunction
	public interface Resolver<S, E> {
		void call(Fn.Arg<S> resolve, Fn.Arg<E> reject);
	}

	@JsFunction
	public interface OpenResolver<S, E> {
		void call(Fn.Arg<S> resolve, Fn.Arg<E> reject);
	}
}
