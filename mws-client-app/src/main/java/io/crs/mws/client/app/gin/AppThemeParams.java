package io.crs.mws.client.app.gin;

import gwt.material.design.client.constants.Color;
import io.crs.mws.client.core.resources.BlueGreyThemeColors;
import io.crs.mws.client.core.resources.ThemeParams;

public class AppThemeParams implements ThemeParams {

	@Override
	public String getPrimaryColor() {
		return BlueGreyThemeColors.C_PRIMARY;
	}

	@Override
	public String getPrimaryLightColor() {
		return BlueGreyThemeColors.C_PRIMARY_LIGHT;
	}

	@Override
	public String getSecondaryColor() {
		return BlueGreyThemeColors.C_SECONDARY;
	}

	@Override
	public String getSecondaryLightColor() {
		return BlueGreyThemeColors.C_SECONDARY_LIGHT;
	}

	@Override
	public String getSecondaryDarkColor() {
		return BlueGreyThemeColors.C_SECONDARY_DARK;
	}

	@Override
	public Color getPrimaryColorX() {
		return Color.valueOf(BlueGreyThemeColors.C_PRIMARYx);
	}

	@Override
	public Color getPrimaryLightColorX() {
		return Color.valueOf(BlueGreyThemeColors.C_PRIMARY_LIGHTx);
	}

	@Override
	public Color getSecondaryColorX() {
		return Color.valueOf(BlueGreyThemeColors.C_PRIMARYx);
	}

	@Override
	public Color getSecondaryLightColorX() {
		return Color.valueOf(BlueGreyThemeColors.C_PRIMARYx);
	}

	@Override
	public Color getSecondaryDarkColorX() {
		return Color.valueOf(BlueGreyThemeColors.C_PRIMARYx);
	}
}
