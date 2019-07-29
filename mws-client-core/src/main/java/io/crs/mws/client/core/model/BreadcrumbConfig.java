/**
 * 
 */
package io.crs.mws.client.core.model;

import gwt.material.design.client.constants.IconType;

/**
 * @author robi
 *
 */
public class BreadcrumbConfig {

	private Integer level;
	private IconType icon;
	private String text;
	private String targetHistory;

	public BreadcrumbConfig(Integer level, IconType icon, String text, String targetHistory) {
		this.level = level;
		this.text = text;
		this.icon = icon;
		this.targetHistory = targetHistory;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public IconType getIcon() {
		return icon;
	}

	public void setIcon(IconType icon) {
		this.icon = icon;
	}

	public String getTargetHistory() {
		return targetHistory;
	}

	public void setTargetHistory(String targetHistory) {
		this.targetHistory = targetHistory;
	}

	@Override
	public String toString() {
		return "BreadcrumbConfig [level=" + level + ", icon=" + icon + ", text=" + text + ", targetHistory="
				+ targetHistory + "]";
	}
}
