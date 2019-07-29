/**
 * 
 */
package io.crs.mws.shared.exception.cnst;

import java.io.Serializable;

/**
 * @author robi
 *
 */
public enum ErrorTitleCode implements Serializable {

	// LOGIN
	LOGIN_INSUFFICIENT_AUTHENTICATION,

	LOGIN_USERNAME_NOT_FOUND,

	LOGIN_BAD_CREDENTIALS,

	LOGIN_DISABLED_USER,

	LOGIN_UNKNOWN_PROBLEM,

	//
	MISMATCHED_ACCOUNT,

	MISSING_VALUE,

	FAILD_ENTITIY_VALIDATION,

	// CRUD MESSAGES
	CRUD_CANNOT_BE_SAVED,

	CRUD_CANNOT_BE_DELETED,

	UNKNOWN;
}
