/**
 * 
 */
package io.crs.mws.shared.cnst;

import java.io.Serializable;

/**
 * @author robi
 *
 */
public enum UserPerm implements Serializable {
	UP_HKSUPERVISOR, UP_HOUSEKEEPER, UP_MAINTMANAGER, UP_TECHNICIAN, UP_RECEPTIONIST, UP_ADMIN;
}