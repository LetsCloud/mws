/**
 * 
 */
package io.crs.mws.shared.cnst;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CR
 *
 */
public class Permissions {
	public final static String ROLE_EHK = "roleEhk";
	public final static String ROLE_AEHK = "roleAehk";

	public final static String PERM_MENU_BOSS = "permMenuBoss";

	public final static String PERM_MENU_HK = "permMenuHk";
	public final static String PERM_HK_ATENDANT = "permHkAtendant";
	public final static String PERM_HK_ASSIGNMENT = "permHkAssignment";

	public final static String PERM_MENU_CONFIG = "permMenuConfig";
	public final static String PERM_CONFIG_USER = "permConfigUser";
	public final static String PERM_CONFIG_USER_EDIT = "permConfigUserEdit";
	public final static String PERM_CONFIG_ROLE = "permConfigRole";
	public final static String PERM_CONFIG_ROLE_EDIT = "permConfigRoleEdit";

	@SuppressWarnings("serial")
	public static final Map<String, String[]> DEFAULT_ROLE_PERMSSIONS = Collections
			.unmodifiableMap(new HashMap<String, String[]>() {
				{
					put(ROLE_EHK, new String[] { PERM_HK_ATENDANT, PERM_HK_ASSIGNMENT });
					put(ROLE_AEHK, new String[] { PERM_HK_ATENDANT, PERM_HK_ASSIGNMENT });
				}
			});

	@SuppressWarnings("serial")
	public static final Map<String, String[]> RELATIONS = Collections.unmodifiableMap(new HashMap<String, String[]>() {
		{
			put(PERM_MENU_BOSS, new String[] {});

			put(PERM_MENU_HK, new String[] { PERM_HK_ATENDANT, PERM_HK_ASSIGNMENT });
			put(PERM_HK_ATENDANT, new String[] {});
			put(PERM_HK_ASSIGNMENT, new String[] {});

			put(PERM_MENU_CONFIG, new String[] { PERM_CONFIG_USER, PERM_CONFIG_ROLE });
			put(PERM_CONFIG_USER, new String[] { PERM_CONFIG_USER_EDIT });
			put(PERM_CONFIG_USER_EDIT, new String[] {});
			put(PERM_CONFIG_ROLE, new String[] { PERM_CONFIG_ROLE_EDIT });
			put(PERM_CONFIG_ROLE_EDIT, new String[] {});
		}
	});

	public static String permMenuHk() {
		return PERM_MENU_HK;
	}
}
