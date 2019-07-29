/**
 * 
 */
package io.crs.mws.shared.dto.auth;

import io.crs.mws.shared.dto.Dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class LoginRequest implements Dto {
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}