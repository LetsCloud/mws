/**
 * 
 */
package io.crs.mws.client.app.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * @author CR
 *
 */
public interface AppMessages extends Messages {

	/*
	 * MAIN MENU
	 */

	@DefaultMessage("Wall")
	String mainMenuItemWall();

	@DefaultMessage("Spots")
	String mainMenuItemSpots();

	@DefaultMessage("Office")
	String mainMenuOffice();
	
	@DefaultMessage("Organizations")
	String menuItemOrganizations();
	
	// Common	
	@DefaultMessage("Common")
	String mainMenuCommonConfig();
	
	@DefaultMessage("Users & permissions")
	String menuItemUserConfig();

	@DefaultMessage("Profiles")
	String menuItemProfileConfig();

	@DefaultMessage("Document properties")
	String menuItemDocConfig();

	
	// Front Office
	@DefaultMessage("Front Office Config")
	String mainMenuFroConfig();

	@DefaultMessage("Hotel Config")
	String menuItemHotelConfig();
	

	// Housekeeping
	@DefaultMessage("Housekeeping Config")
	String mainMenuKipConfig();
	

	// Management Information
	@DefaultMessage("Management Information")
	String mainMenuInfConfig();
	
	

	
	/*
	 * LOGIN
	 */

	@DefaultMessage("LOGIN")
	String loginCaption();

	@DefaultMessage("Email")
	String loginEmail();

	@DefaultMessage("Password")
	String loginPassword();

	@DefaultMessage("Sign in")
	String loginSubmit();

	@DefaultMessage("Remember Me")
	String loginRememberMe();

	@DefaultMessage("Sign Up")
	String loginSignUp();
	
	@DefaultMessage("OR LOGIN WITH A SOCIAL ACCOUNT") 
	String loginSocialHeader();
	
	@DefaultMessage("Login with Google") 
	String loginWithGoogle();
	
	

	
	/*
	 * SIGN UP
	 */

	@DefaultMessage("SIGN UP")
	String signupCaption();

	@DefaultMessage("Nickname")
	String signupNickname();

	@DefaultMessage("Email")
	String signupEmail();

	@DefaultMessage("Password")
	String signupPassword();

	@DefaultMessage("Confirm password")
	String signupConfirm();

	@DefaultMessage("Sign Me Up")
	String signupSubmit();

	@DefaultMessage("By signing up, you agree to the")
	String signupYouAgree();

	@DefaultMessage("terms and conditions.")
	String signupConditions();

	@DefaultMessage("Already registred?")
	String signupAlreadyRegistred();

	@DefaultMessage("Log In")
	String signupLogin();
}
