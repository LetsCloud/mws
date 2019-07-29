/**
 * 
 */
package io.crs.mws.server.security.social;

import org.springframework.security.core.AuthenticationException;

/**
 * @author robi
 *
 */
public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }

}
