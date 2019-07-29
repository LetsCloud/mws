/**
 * 
 */
package io.crs.mws.client.core.firebase;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.crs.mws.client.core.firebase.messaging.MessagingRegistration;
import io.crs.mws.client.core.firebase.messaging.js.Fnx;
import io.crs.mws.client.core.firebase.messaging.js.Messaging;

/**
 * @author robi
 *
 */
public class FirebaseRegister {

    private final Map<HandlerRegistration, Boolean> handlerRegistrations = Maps.newHashMap();

    public HandlerRegistration addMessaging(Messaging messaging, Fnx.Arg fn, boolean on) {
        return add(new MessagingRegistration(messaging, fn), false, on);
}
    public HandlerRegistration add(HandlerRegistration handlerRegistration, boolean auth, boolean on) {
        handlerRegistrations.put(handlerRegistration, auth);
        if (on) {
            handlerRegistration.on();
        } else {
            handlerRegistration.off();
        }
        return handlerRegistration;
    }

    public void enableFirebaseEvents() {
        Sets.newHashSet(handlerRegistrations.keySet()).forEach(HandlerRegistration::on);
    }

    public void disableFirebaseEvents() {
        Sets.newHashSet(handlerRegistrations.keySet()).forEach(HandlerRegistration::off);
    }

    public void removeRegistration(HandlerRegistration handlerRegistration) {
        if (handlerRegistration != null) {
            handlerRegistration.off();
            handlerRegistrations.remove(handlerRegistration);
        }
    }

    public void removeFirebaseAuthEvents() {
        Set<HandlerRegistration> authHandlerRegistrations = Maps
                .filterValues(handlerRegistrations, auth -> auth).keySet();
        Sets.newHashSet(authHandlerRegistrations).forEach(this::removeRegistration);
    }

    public void removeAllRegistrations() {
        Sets.newHashSet(handlerRegistrations.keySet()).forEach(this::removeRegistration);
    }
}
