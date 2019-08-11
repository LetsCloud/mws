/**
 * 
 */
package io.crs.mws.client.core.i18n;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.Messages.DefaultMessage;

/**
 * @author CR
 *
 */
public interface CoreMessages extends Messages {
	/*
	 * Common
	 */
	@DefaultMessage("Yes")
	String comYes();

	@DefaultMessage("No")
	String comNo();

	@DefaultMessage("OK")
	String comOk();

	@DefaultMessage("Close")
	String comClose();

	@DefaultMessage("Create")
	String comCreate();

	@DefaultMessage("Edit")
	String comEdit();

	@DefaultMessage("Save")
	String comSave();

	@DefaultMessage("Cancel")
	String comCancel();

	@DefaultMessage("Delete")
	String comDelete();

	@DefaultMessage("Browse")
	String comBrowse();

	@DefaultMessage("Active")
	String comActive();

	@DefaultMessage("Inactive")
	String comInactive();

	@DefaultMessage("Required field")
	String comRequiredField();

	@DefaultMessage("")
	String com1SecondAgo(String time);

	@DefaultMessage("")
	String com2SecondsAgo(String time);

	@DefaultMessage("")
	String com5SecondsAgo(String time);

	@DefaultMessage("")
	String com1MinuteAgo(String time);

	@DefaultMessage("")
	String com2MinutesAgo(String time);

	@DefaultMessage("")
	String com5MinutesAgo(String time);

	@DefaultMessage("")
	String com1HourAgo(String time);

	@DefaultMessage("")
	String com2HoursAgo(String time);

	@DefaultMessage("")
	String com5HoursAgo(String time);

	@DefaultMessage("")
	String com1DayAgo(String time);

	@DefaultMessage("")
	String com2DaysAgo(String time);

	@DefaultMessage("")
	String com5DaysAgo(String time);

	@DefaultMessage("")
	String com1WeekAgo(String time);

	@DefaultMessage("")
	String com2WeeksAgo(String time);

	@DefaultMessage("")
	String com5WeeksAgo(String time);

	@DefaultMessage("")
	String com1MonthAgo(String time);

	@DefaultMessage("")
	String com2MonthsAgo(String time);

	@DefaultMessage("")
	String com5MonthsAgo(String time);

	@DefaultMessage("")
	String com1YearAgo(String time);

	/*
	 * DIALOG MESSAGES
	 */
	@DefaultMessage("DELETION")
	String dialogDeleteTitle();

	/*
	 * CRUD MESSAGES
	 */
	@DefaultMessage("Data cannot be saved")
	String CRUD_CANNOT_BE_SAVED();

	@DefaultMessage("Data cannot be deleted")
	String CRUD_CANNOT_BE_DELETED();

	@DefaultMessage("The task group code {0} already exists.")
	String TASKGROUP_CODE_ALREADY_EXISTS(String code);

	@DefaultMessage("The task group is used by Task Todo.")
	String TASKGROUP_ID_USED_BY_TASKTODO();

	@DefaultMessage("The task group is used by Task Type.")
	String TASKGROUP_ID_USED_BY_TASKTYPE();

	@DefaultMessage("Overlaping OOO period withh room {0} ({1} - {2}).")
	String OOO_ROOM_OVERLAP(String room, String fromDate, String todate);

	/*
	 * LOGIN
	 */
	@DefaultMessage("Login")
	String loginCaption();

	@DefaultMessage("Account")
	String loginAccount();

	@DefaultMessage("Username")
	String loginUsername();

	@DefaultMessage("password")
	String loginPassword();

	@DefaultMessage("LOG IN")
	String loginSubmit();

	@DefaultMessage("Keep me logged in")
	String loginKeepMe();

	@DefaultMessage("Create an Account")
	String loginCreate();

	@DefaultMessage("Insufficient authentication!")
	String loginInsufficientAuthentication();

	@DefaultMessage("Username not found!")
	String loginUsernameNotFound();

	@DefaultMessage("Bad credentials!")
	String loginErrorBadCredentials();

	@DefaultMessage("The user is disabled!")
	String loginErrorDisabledUser();

	@DefaultMessage("Umknwon login problem!")
	String loginErrorUnknownProblem();

	@DefaultMessage("Sign in Faild - Wrong email or password!")
	String loginFaildSignIn();

	/*
	 * REGISTER
	 */
	@DefaultMessage("Register")
	String regCaption();

	@DefaultMessage("Account name")
	String regAccountName();

	@DefaultMessage("Street and number")
	String regStreet();

	@DefaultMessage("City")
	String regCity();

	@DefaultMessage("Postcode")
	String regPostcode();

	@DefaultMessage("Country")
	String regCountry();

	@DefaultMessage("Username")
	String regUsername();

	@DefaultMessage("Your email address")
	String regEmail();

	@DefaultMessage("Repeat email")
	String regEmail2();

	@DefaultMessage("Password")
	String regPassword();

	@DefaultMessage("Repeat password")
	String regPassword2();

	@DefaultMessage("Submit")
	String regSubmit();

	/*
	 * SUCCESS
	 */
	@DefaultMessage("Registration Success - Congratulations!")
	String sucCaption();

	@DefaultMessage("You have successfully complited your registration.")
	String sucLine1();

	@DefaultMessage("Your account id: {0}")
	String sucLine2(String id);

	@DefaultMessage("Please check your email as we have sent you all your "
			+ "registration details and activate your account!")
	String sucLine3();

	/*
	 * ACTIVATE
	 */
	@DefaultMessage("Account activation")
	String actCaption();

	@DefaultMessage("Your account is now active!")
	String actLine1();

	@DefaultMessage("Clicking on the link below you can login and start your work:")
	String actLine2();

	/*
	 * SIDE PROFILE
	 */
	@DefaultMessage("Account setup")
	String accountMenuItemSetup();

	@DefaultMessage("System setup")
	String accountMenuItemSystem();

	@DefaultMessage("User profile")
	String userMenuItemProfile();

	@DefaultMessage("Switch user")
	String userMenuItemSwitch();

	@DefaultMessage("Log Out")
	String userMenuItemLogout();

	/*
	 * SIDE NAV
	 */
	@DefaultMessage("Configuration")
	String mainMenuGroupConfig();

	@DefaultMessage("Hotels")
	String mainMenuItemHotels();

	@DefaultMessage("Users")
	String mainMenuItemUsers();

	@DefaultMessage("Roles")
	String mainMenuItemRoles();

	/*
	 * BREADCRUMBS
	 */

	@DefaultMessage("Home")
	String breadcrumbsHome();

	@DefaultMessage("Users")
	String breadcrumbsUsersTable();

	@DefaultMessage("Editor")
	String breadcrumbsUserEditor();

	/*
	 * DASHBOARD
	 */
	@DefaultMessage("Dashboard")
	String pageDashboardTitle();

	/*
	 * SYSTEM CONFIG
	 */

	@DefaultMessage("System configuration")
	String systemConfigTitle();

	@DefaultMessage("Basic settings that determine the operation of the system")
	String systemConfigDescription();

	/*
	 * USER GROUP BROWSER
	 */

	@DefaultMessage("User Groups")
	String userGroupBrowserTitle();

	@DefaultMessage("Name")
	String userGroupBrowserName();

	/*
	 * USER_GROUP EDITOR
	 */

	@DefaultMessage("Create User Group")
	String userGroupEditorCreateTitle();

	@DefaultMessage("Modify User Group")
	String userGroupEditorModifyTitle();

	@DefaultMessage("Name")
	String userGroupEditorName();

	/*
	 * USER BROWSER
	 */

	@DefaultMessage("Users")
	String userBrowserTitle();

	@DefaultMessage("Username")
	String usersBrowserUsername();

	@DefaultMessage("Email")
	String usersTableEmail();

	/*
	 * USER EDITOR
	 */

	@DefaultMessage("Create User Profile")
	String userEditorCreateTitle();

	@DefaultMessage("Modify User Profile")
	String userEditorModifyTitle();

	@DefaultMessage("Name")
	String userEditorName();

	@DefaultMessage("Title")
	String userEditorTitle();

	@DefaultMessage("Email")
	String userEditorEmail();

	@DefaultMessage("Code")
	String userEditorCode();

	@DefaultMessage("Username")
	String userEditorUsername();

	@DefaultMessage("Enabled")
	String userEditorEnabled();

	@DefaultMessage("Admin user")
	String userEditorIsAdmin();

	@DefaultMessage("Permitted hotels")
	String userEditorAvailableHotels();

	@DefaultMessage("Chhoose a hotel")
	String userEditorChooseHotel();

	@DefaultMessage("Deafult hotel")
	String userEditorDefaultHotel();

	@DefaultMessage("Chat groups")
	String userEditorChatGroups();

	@DefaultMessage("Choose a group")
	String userEditorChooseGroup();

	@DefaultMessage("Password")
	String userEditorPassword();

	/*
	 * TRANSLATE EDITOR
	 */
	@DefaultMessage("Language")
	String translateEditorLanguage();

	@DefaultMessage("Choose a language")
	String translateEditorLanguagePlaceholder();

	@DefaultMessage("Text")
	String translateEditorText();

	/*
	 * TASK GROUP BROWSER
	 */
	@DefaultMessage("Task Groups")
	String taskGroupBrowserTitle();

	@DefaultMessage("Kind")
	String taskGroupBrowserKindColumn();

	@DefaultMessage("Code")
	String taskGroupBrowserCodeColumn();

	@DefaultMessage("Description")
	String taskGroupBrowserDescriptionColumn();

	@DefaultMessage("Delete Task Group(s)")
	String taskGroupBrowserDeleteTitle();

	@DefaultMessage("Are you sure you want to delete the following task group(s): {0}")
	String taskGroupBrowserDeleteMessage(String codes);

	/*
	 * TASK GROUP FILTER
	 */

	@DefaultMessage("Task Kind")
	String taskGroupFilterTaskKindLabel();

	@DefaultMessage("Choose a Task Kind")
	String taskGroupFilterTaskKindPlaceholder();

	/*
	 * TASK GROUP EDITOR
	 */
	@DefaultMessage("Create Task Group")
	String taskGroupEditorCreateTitle();

	@DefaultMessage("Task Kind")
	String taskGroupEditorKind();

	@DefaultMessage("Choose a Task Kind")
	String taskGroupEditorKindPlaceholder();

	@DefaultMessage("Modify Task Group")
	String taskGroupEditorModifyTitle();

	@DefaultMessage("Code")
	String taskGroupEditorCode();

	@DefaultMessage("Description")
	String taskGroupEditorDescription();

	/*
	 * TASK TODO BROWSER
	 */
	@DefaultMessage("Task Todos")
	String taskTodoBrowserTitle();

	@DefaultMessage("Kind")
	String taskTodoBrowserKindColumn();

	@DefaultMessage("Description")
	String taskTodoBrowserDescriptionColumn();

	@DefaultMessage("Time(m)")
	String taskTodoBrowserTimeReqColumn();

	@DefaultMessage("Delete Task Todo(s)")
	String taskTodoBrowserDeleteTitle();

	@DefaultMessage("Are you sure you want to delete the following task todo(s): {0}")
	String taskTodoBrowserDeleteMessage(String codes);

	/*
	 * TASK TODO FILTER
	 */

	@DefaultMessage("Task Group")
	String taskTodoFilterTaskGroupLabel();

	@DefaultMessage("Choose a Task Group")
	String taskTodoFilterTaskGroupPlaceholder();

	/*
	 * TASK TODO EDITOR
	 */
	@DefaultMessage("Create Task Todo")
	String taskTodoEditorCreateTitle();

	@DefaultMessage("Modify Task Todo")
	String taskTodoEditorModifyTitle();

	@DefaultMessage("Task Kind")
	String taskTodoEditorKind();

	@DefaultMessage("Choose a Task Kind")
	String taskTodoEditorKindPlaceholder();

	@DefaultMessage("Task Type")
	String taskTodoEditorType();

	@DefaultMessage("Choose a Task Type")
	String taskTodoEditorTypePlaceholder();

	@DefaultMessage("Description")
	String taskTodoEditorDescription();

	@DefaultMessage("Time required(m)")
	String taskTodoEditorTimeRequired();

	/*
	 * TASK TYPE BROWSER
	 */
	@DefaultMessage("Task Types")
	String taskTypeBrowserTitle();

	@DefaultMessage("Kind")
	String taskTypeBrowserKindColumn();

	@DefaultMessage("Group")
	String taskTypeBrowserGroupColumn();

	@DefaultMessage("Code")
	String taskTypeBrowserCodeColumn();

	@DefaultMessage("Description")
	String taskTypeBrowserDescriptionColumn();

	@DefaultMessage("Time(m)")
	String taskTypeBrowserTimeReqColumn();

	/*
	 * TASK TYPE EDITOR
	 */
	@DefaultMessage("Create Task Type")
	String taskTypeEditorCreateTitle();

	@DefaultMessage("Modify Task Type")
	String taskTypeEditorModifyTitle();

	@DefaultMessage("Task Kind")
	String taskTypeEditorKind();

	@DefaultMessage("Choose a Task Kind")
	String taskTypeEditorKindPlaceholder();

	@DefaultMessage("Task Group")
	String taskTypeEditorGroup();

	@DefaultMessage("Choose a Task Group")
	String taskTypeEditorGroupPlaceholder();

	@DefaultMessage("Code")
	String taskTypeEditorCode();

	@DefaultMessage("Description")
	String taskTypeEditorDescription();

	@DefaultMessage("Translations")
	String taskTypeEditorTranslations();

	@DefaultMessage("Time required(m)")
	String taskTypeEditorTimeRequired();

	@DefaultMessage("Todos")
	String taskTypeEditorTodos();

	@DefaultMessage("Add Todo")
	String taskTypeEditorAddTodo();

	@DefaultMessage("Delete Todo")
	String taskTypeEditorDeleteTodo();

	/*
	 * PROFILE CONFIG
	 */

	@DefaultMessage("Profile configuration")
	String profileConfigTitle();

	@DefaultMessage("Manage your organization's profile or contact information")
	String profileConfigDescription();

	/*
	 * RELATIONSHIP BROWSER
	 */
	@DefaultMessage("Relationships")
	String relationshipBrowserTitle();

	@DefaultMessage("Forward")
	String relationshipBrowserColForward();

	@DefaultMessage("Reverse")
	String relationshipBrowserColReverse();

	@DefaultMessage("Active")
	String relationshipBrowserColActive();

	/*
	 * RELATIONSHIPP EDITOR
	 */

	@DefaultMessage("Create Relationship Group")
	String relationshipCreateTitle();

	@DefaultMessage("Edit Relationship Group")
	String relationshipEditTitle();

	@DefaultMessage("Forward")
	String relationshipFldForward();

	@DefaultMessage("Reverse")
	String relationshipFldReverse();

	/*
	 * PROFILE GROUP BROWSER
	 */

	@DefaultMessage("Profile Groups")
	String profileGroupBrowserTitle();

	@DefaultMessage("Code")
	String profileGroupBrowserCode();

	@DefaultMessage("Description")
	String profileGroupBrowserDescription();

	@DefaultMessage("Active")
	String profileGroupBrowserActive();

	/*
	 * DOCUMENT CONFIG
	 */

	@DefaultMessage("Profile configuration")
	String documentConfigTitle();

	@DefaultMessage("Manage document properties")
	String documentConfigDescription();

	/*
	 * QUOTATION STATUS BROWSER
	 */
	@DefaultMessage("Quotation Statuses")
	String quotationStatusBrowserTitle();

	@DefaultMessage("Code")
	String quotationStatusBrowserCode();

	@DefaultMessage("Description")
	String quotationStatusBrowserDescription();

	/*
	 * QUOTATION STATUS EDITOR
	 */

	@DefaultMessage("Create Quotation Status")
	String quotationStatusCreateTitle();

	@DefaultMessage("Modify Quotation Status")
	String quotationStatusEditTitle();

	@DefaultMessage("Code")
	String quotationStatusCode();

	@DefaultMessage("Description")
	String quotationStatusDescription();

	/*
	 * ORGANIZATION CONFIG
	 */
	@DefaultMessage("Organizations Data")
	String organizationConfigTitle();

	@DefaultMessage("Organizations ...")
	String organizationConfigDescription();

	/*
	 * ORGANIZATION BROWSER
	 */

	@DefaultMessage("Organizations")
	String organizationBrowserTitle();

	@DefaultMessage("Code")
	String organizationBrowserColCode();

	@DefaultMessage("Name")
	String organizationBrowserColName();

	/*
	 * PROFILE FILTER
	 */

	@DefaultMessage("Code filter")
	String profileFilterCodeLabel();

	@DefaultMessage("Code~")
	String profileFilterCode();

	@DefaultMessage("Name filter")
	String profileFilterNameLabel();

	@DefaultMessage("Name~")
	String profileFilterName();

	@DefaultMessage("ProfileGroup filter")
	String profileFilterProfileGroupLabel();

	@DefaultMessage("Group~")
	String profileFilterProfileGroup();

	/*
	 * ORGANIZATION CREATOR
	 */
	@DefaultMessage("Create Organization")
	String organizationCreatorTitle();

	@DefaultMessage("Common data...")
	String organizationCreatorDescription();

	/*
	 * ORGANIZATION EDITOR
	 */
	@DefaultMessage("Display Organization Profile")
	String organizationEditorDisplayTitle();

	@DefaultMessage("Modify Organization Profile")
	String organizationEditorModifyTitle();

	@DefaultMessage("General Data")
	String organizationEditorDescription();

	@DefaultMessage("Organization name")
	String organizationEditorName();

	@DefaultMessage("Code")
	String organizationEditorCode();

	@DefaultMessage("Profile Group")
	String organizationEditorProfileGroup();

	@DefaultMessage("Communication")
	String organizationEditorCommunication();

	@DefaultMessage("Addresses")
	String organizationEditorAdresses();

	@DefaultMessage("Web Presence")
	String organizationEditorWebPresence();

	/*
	 * QUOTATION BROWSER
	 */
	@DefaultMessage("Quotations")
	String quotationBrowserTitle();

	@DefaultMessage("Code")
	String quotationBrowserCode();

	@DefaultMessage("Description")
	String quotationBrowserDescription();

	@DefaultMessage("Organization")
	String quotationBrowserOrganization();

	@DefaultMessage("Issued By")
	String quotationBrowserIssuedBy();

	@DefaultMessage("Issue Date")
	String quotationBrowserIssueDate();

	/*
	 * QUOTATION FILTER
	 */

	@DefaultMessage("Code~")
	String quotationFilterCodeChipLabel();

	@DefaultMessage("Code filter")
	String quotationFilterCodeLabel();

	@DefaultMessage("Description~")
	String quotationDescriptionChipLabel();

	@DefaultMessage("Description filter")
	String quotationFilterDescriptionLabel();

	@DefaultMessage("Status filter")
	String quotationFilterStatusLabel();

	/*
	 * QUOTATION EDITOR
	 */
	@DefaultMessage("Create Quotation")
	String quotationEditorCreateTitle();

	@DefaultMessage("Create Quotation")
	String quotationEditorCreateDescription();

	@DefaultMessage("Modify Quotation")
	String quotationEditorModifyTitle();

	@DefaultMessage("Modify Quotation")
	String quotationEditorModifyDescription();

	@DefaultMessage("Code")
	String quotationEditorCode();

	@DefaultMessage("Status")
	String quotationEditorStatus();

	@DefaultMessage("Description")
	String quotationEditorDescription();

	@DefaultMessage("Organization")
	String quotationEditorOrganization();

	@DefaultMessage("Issued by")
	String quotationEditorIssuedBy();

	@DefaultMessage("Issue Date")
	String quotationEditorIssueDate();

	/*
	 * CONTACT BROWSER
	 */
	@DefaultMessage("Contacts")
	String contactBrowserTitle();

	@DefaultMessage("Name")
	String contactBrowserColName();

	/*
	 * CONTACT CREATOR
	 */
	@DefaultMessage("Create Organization")
	String contactCreatorTitle();

	@DefaultMessage("Common data...")
	String contactCreatorDescription();

	/*
	 * CONTACT DISPLAY
	 */
	@DefaultMessage("Contact Profile View")
	String contactDisplayTitle();

	@DefaultMessage("Contact information ...")
	String contactDisplayDescription();

	/*
	 * CONTACT EDITOR
	 */
	@DefaultMessage("Edit Contact Profile")
	String contactEditorTitle();

	@DefaultMessage("General Data")
	String contactEditorDescription();

	@DefaultMessage("Contact name")
	String contactEditorName();

	@DefaultMessage("Profile Group")
	String contactEditorProfileGroup();

	@DefaultMessage("Links")
	String contactEditorLink();

	@DefaultMessage("Communication")
	String contactEditorCommunication();

	@DefaultMessage("Addresses")
	String contactEditorAdresses();

	@DefaultMessage("Web Presence")
	String contactEditorWebPresence();

	@DefaultMessage("Profile operations")
	String contactDisplayFAB();

	@DefaultMessage("Delete Profile")
	String contactDisplayDelete();

	/*
	 * HOTEL CONFIG
	 */
	@DefaultMessage("Hotel Configuration")
	String hotelConfigTitle();

	@DefaultMessage("Hotel Configuration...")
	String hotelConfigDescription();

	/*
	 * COMMUNICATION EDITOR
	 */
	@DefaultMessage("Mode")
	String communicationEditorLabel();

	@DefaultMessage("Primary")
	String communicationEditorPrimary();

	/*
	 * ADDRESS EDITOR
	 */

	@DefaultMessage("Address Type")
	String addressEditorLabel();

	@DefaultMessage("Choose an Address Type")
	String addressEditorLabelPlaceholder();

	@DefaultMessage("Primary")
	String addressEditorPrimary();

	@DefaultMessage("Street and number")
	String addressEditorStreet();

	@DefaultMessage("Country")
	String addressEditorCountry();

	@DefaultMessage("Choose a Coountry")
	String addressEditorCountryPlaceholder();

	@DefaultMessage("Postal code")
	String addressEditorPostcode();

	@DefaultMessage("Choose an Postal code")
	String addressEditorPostcodePlaceholder();

	@DefaultMessage("City")
	String addressEditorCity();

	/*
	 * HOTEL BROWSER
	 */

	@DefaultMessage("Hotels Data")
	String hotelBrowserTitle();

	@DefaultMessage("Code")
	String hotelsTableCode();

	@DefaultMessage("Name")
	String hotelsTableName();

	/*
	 * HOTEL EDITOR
	 */

	@DefaultMessage("Create a Hotel")
	String hotelEditorCreateTitle();

	@DefaultMessage("Modify Hotel Data")
	String hotelEditorModifyTitle();

	@DefaultMessage("Code")
	String hotelEditorCode();

	@DefaultMessage("Name")
	String hotelEditorName();

	/*
	 * ROOMTYPE BROWSER
	 */

	@DefaultMessage("Room Types")
	String roomTypeBrowserTitle();

	@DefaultMessage("Choose a hotel")
	String roomTypesTableHotelsPlaceholder();

	@DefaultMessage("Selected hotel")
	String roomTypesTableHotelsLabel();

	@DefaultMessage("Only active types")
	String roomTypesTableOnlyActive();

	@DefaultMessage("Code")
	String roomTypesTableCode();

	@DefaultMessage("Name")
	String roomTypesTableName();

	@DefaultMessage("InventoryType")
	String roomTypesTableInventoryType();

	@DefaultMessage("Number Of Rooms")
	String roomTypesTableNumberOfRooms();

	@DefaultMessage("Active")
	String roomTypesTableActive();

	@DefaultMessage("Inactive")
	String roomTypesTableInactive();

	/*
	 * ROOMTYPE FILTER
	 */

	@DefaultMessage("Choose inventory type")
	String roomTypeFilterInventoryTypePlaceholder();

	@DefaultMessage("Selected inventory type")
	String roomTypeFilterInventoryTypeLabel();

	/*
	 * ROOMTYPE EDITOR
	 */

	@DefaultMessage("Create room type")
	String roomTypeEditorCreateTitle();

	@DefaultMessage("Modify room type")
	String roomTypeEditorModifyTitle();

	@DefaultMessage("Active")
	String roomTypeEditorActive();

	@DefaultMessage("Code")
	String roomTypeEditorCode();

	@DefaultMessage("Name")
	String roomTypeEditorName();

	@DefaultMessage("Description")
	String roomTypeEditorDescription();

	@DefaultMessage("Channel manager/Booking engine")
	String roomTypeEditorChmGroup();

	@DefaultMessage("Inventory Type")
	String roomTypeEditorInventoryType();

	@DefaultMessage("Choose inventory type")
	String roomTypeEditorPlaceholderInventoryType();

	@DefaultMessage("Housekeeping")
	String roomTypeEditorHkGroup();

	@DefaultMessage("Number of beds")
	String roomTypeEditorNumOfBeds();

	@DefaultMessage("Number of extra beds")
	String roomTypeEditorNumOfXtrBeds();

	@DefaultMessage("Cleaning factor")
	String roomTypeEditorCleaningFactor();

	/*
	 * ROOMS TABLE
	 */

	@DefaultMessage("Rooms")
	String roomBrowserTitle();

	@DefaultMessage("Code")
	String roomsTableCode();

	@DefaultMessage("Floor")
	String roomsTableFloor();

	@DefaultMessage("Type")
	String roomsTableType();

	/*
	 * ROOM FILTER
	 */

	@DefaultMessage("Selected floor")
	String roomFilterFloorLabel();

	@DefaultMessage("Choose floor")
	String roomFilterFloorPlaceholder();

	@DefaultMessage("Floor=")
	String roomFilterFloor();

	@DefaultMessage("Selected Room Types")
	String roomFilterRoomTypesLabel();

	@DefaultMessage("Choose Room Type")
	String roomFilterRoomTypesPlaceholder();

	/*
	 * ROOM EDITOR
	 */

	@DefaultMessage("Create room")
	String roomEditorCreateTitle();

	@DefaultMessage("Modify room")
	String roomEditorModifyTitle();

	@DefaultMessage("Code")
	String roomEditorCode();

	@DefaultMessage("Floor")
	String roomTypeEditorFloor();

	@DefaultMessage("Choose Room Type")
	String roomEditorChooseRoomtype();

	@DefaultMessage("Room Type")
	String roomEditorRoomtype();

	@DefaultMessage("Description")
	String roomEditorDescription();

	@DefaultMessage("Availability")
	String roomEditorAvailability();

	@DefaultMessage("From Date")
	String roomEditorAvailabilityFromDate();

	@DefaultMessage("Add")
	String roomEditorAvailabilityAdd();

	@DefaultMessage("Remove")
	String roomEditorAvailabilityRemove();

	/*
	 * MARKET GROUP BROWSER
	 */

	@DefaultMessage("Market Groups")
	String marketGroupBrowserTitle();

	/*
	 * SEND MESSAGE
	 */

	@DefaultMessage("Type a message")
	String sendMessagePlaceHolder();

	/*
	 * CREATE CHAT
	 */

	@DefaultMessage("Create a Chat")
	String createChatHeader();

	@DefaultMessage("Feel free to communicate")
	String createChatSubHeader();

	@DefaultMessage("Invited groups and users")
	String createChatInvited();

	@DefaultMessage("Choose group or user")
	String createChatToInvite();

	@DefaultMessage("Groups")
	String createChatGroupGroup();

	@DefaultMessage("Users")
	String createChatUserGroup();

}
