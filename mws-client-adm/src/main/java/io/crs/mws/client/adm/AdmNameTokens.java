/**
 * 
 */
package io.crs.mws.client.adm;

/**
 * @author robi
 *
 */
public class AdmNameTokens {

	public static final String HOME = "/home";

	// Common configurations
	public static final String SYSTEM_CONFIG = "/systemConfig";

	public static final String USER_CONFIG = "/userconfig";
	public static final String USERS = "!users";
	public static final String USER_EDITOR = "/userEditor";
	public static final String ROLE_CONFIG = "/roleconfig";

	public static final String PROFILE_CONFIG = "/profleConfig";
	public static final String ORGANIZATION_CREATOR = "/organizationCreator";
	public static final String CUSTOMER_EDITOR = "/customerEditor";
	public static final String CONTACT_CREATOR = "/contactCreator";
	public static final String ORGANIZATION_DISPLAY = "/organizationDisplay";
	public static final String CONTACT_DISPLAY = "/contactDisplay";

	// Hotel configurations
	public static final String HOTEL_CONFIG = "/hotelConfig";
	public static final String HOTEL_EDITOR = "/hotelEditor";
	public static final String ROOMTYPE_EDITOR = "/roomTypeEditor";
	public static final String ROOM_EDITOR = "/roomEditor";

	public static final String RESERVATION_CONFIG = "/hotelConfig";

	// Housekeeping configurations


	// Reservation
	public static final String CREATE_RESERVATION = "/createReservation";
	public static final String RESERVATION = "/reservation";

	public static String getProfileConfig() {
		return PROFILE_CONFIG;
	}


	public static String getHome() {
		return HOME;
	}
/*
	public static String getLogin() {
		return LOGIN;
	}

	public static String getRegister() {
		return REGISTER;
	}

	public static String getSuccess() {
		return SUCCESS;
	}

	public static String getActivate() {
		return ACTIVATE;
	}
*/
	// Configuration
	public static String getHotelConfig() {
		return HOTEL_CONFIG;
	}

	public static String getUserConfig() {
		return USER_CONFIG;
	}

	public static String getRoleConfig() {
		return ROLE_CONFIG;
	}

	public static String getSystemConfig() {
		return SYSTEM_CONFIG;
	}
}
