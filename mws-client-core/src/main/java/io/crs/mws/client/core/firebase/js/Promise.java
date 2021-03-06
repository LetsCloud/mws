/**
 * 
 */
package io.crs.mws.client.core.firebase.js;

import java.util.HashMap;
import java.util.Map;

import io.crs.mws.client.core.promise.xgwt.Error;
import io.crs.mws.client.core.promise.xgwt.Fn;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

/**
 * @author robi
 *
 */
@SuppressWarnings("all")
@JsType(isNative = true, namespace = "firebase", name ="Promise")
public class Promise<S, E> extends Thenable<S, E> {

	@JsConstructor
	public Promise(Fn.Resolver<S, E> resolver) {
	}

	public static native <A, B> Promise<A, B> all(Promise<?, ?>[] promises);

	public static native <S> Promise<S, Error> resolve(S success);

	private Fn.Arg<S> resolve;
	private Fn.Arg<E> reject;

	@JsOverlay
	public final Promise<S, E> openResolve(S success) {
		if (resolve == null) {
			throw new UnsupportedOperationException("promise is not open, use buildOpenPromise");
		}
		resolve.call(success);
		return this;
	}

	@JsOverlay
	public final Promise<S, E> openReject(E error) {
		if (reject == null) {
			throw new UnsupportedOperationException("promise is not open, use buildOpenPromise");
		}
		reject.call(error);
		return this;
	}

	@JsOverlay
	public static <S, E> Promise<S, E> buildOpenPromise() {
		final Map<String, Fn.Arg<?>> containers = new HashMap<>(2);
		Fn.Resolver<S, E> resolver = new Fn.Resolver<S, E>() {
			@Override
			public void call(Fn.Arg<S> resolve, Fn.Arg<E> reject) {
				containers.put("resolve", resolve);
				containers.put("reject", reject);
			}
		};
		Promise<S, E> openPromise = new Promise<S, E>(resolver);
		openPromise.resolve = (Fn.Arg<S>) containers.get("resolve");
		openPromise.reject = (Fn.Arg<E>) containers.get("reject");
		return openPromise;
	}
}