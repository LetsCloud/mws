/**
 * 
 */
package io.crs.mws.shared.dto.menu;

import java.util.ArrayList;
import java.util.List;

import io.crs.mws.shared.cnst.MenuItemType;
import io.crs.mws.shared.dto.AccountChildDto;

/**
 * @author CR
 *
 */
@SuppressWarnings("serial")
public class MenuItemDto extends AccountChildDto {

	public enum MenuItemCode {
		KIP_DASHBOARD, TASK_MANAGER, CHAT_ROOM, ROOM_BROWSER, OOO_BROWSER, PUBLICAREA_BROWSER, ASSIGNMENT_GROUP, ROOM_ASSIGNMENT,
		PUBLICAREA_ASSIGNMENT, MAINTENANCE_ASSIGNMENT, MINIBAR_POSTS, CONFIG_GROUP, USER_CONFIG, PROFILE_CONFIG,
		HOTEL_CONFIG, HOUSEKEEPING_CONFIG, MAINTENANCE_CONFIG
	}

	private MenuItemCode code;

	private Integer index;

	private MenuItemType type;

	private String icon;

	private String text;

	private String nameToken;

	private List<MenuItemDto> items = new ArrayList<MenuItemDto>();

	private List<MenuItemParamDto> params = new ArrayList<MenuItemParamDto>();

	public MenuItemDto() {
	}

	public MenuItemDto(Builder<?> builder) {
		super(builder);
		code = builder.code;
		index = builder.index;
		type = builder.type;
		icon = builder.icon;
		text = builder.text;
		nameToken = builder.nameToken;
		items = builder.items;
		params = builder.params;
	}

	public MenuItemCode getCode() {
		return code;
	}

	public void setCode(MenuItemCode code) {
		this.code = code;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public MenuItemType getType() {
		return type;
	}

	public void setType(MenuItemType type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNameToken() {
		return nameToken;
	}

	public void setNameToken(String nameToken) {
		this.nameToken = nameToken;
	}

	public List<MenuItemParamDto> getParams() {
		return params;
	}

	public void setParams(List<MenuItemParamDto> params) {
		this.params = params;
	}

	public List<MenuItemDto> getItems() {
		return items;
	}

	public void setItems(List<MenuItemDto> items) {
		this.items = items;
	}

	public void addItem(MenuItemDto item) {
		this.items.add(item);
	}

	@Override
	public String toString() {
		return "MenuItemDto [index=" + this.index + ", type=" + this.type + ", icon=" + this.icon + ", text="
				+ this.text + ", nameToken=" + this.nameToken + super.toString() + "]";
	}

	public static abstract class Builder<T extends Builder<T>> extends AccountChildDto.Builder<T> {

		private MenuItemCode code;
		private Integer index;
		private MenuItemType type;
		private String icon;
		private String text;
		private String nameToken;
		private List<MenuItemDto> items = new ArrayList<MenuItemDto>();
		private List<MenuItemParamDto> params = new ArrayList<MenuItemParamDto>();
		private Integer itemIndex = 0;

		public T code(MenuItemCode code) {
			this.code = code;
			return self();
		}

		public T index(Integer index) {
			this.index = index;
			return self();
		}

		public T type(MenuItemType type) {
			this.type = type;
			return self();
		}

		public T icon(String icon) {
			this.icon = icon;
			return self();
		}

		public T text(String text) {
			this.text = text;
			return self();
		}

		public T nameToken(String nameToken) {
			this.nameToken = nameToken;
			return self();
		}

		public T addItem(MenuItemDto item) {
			item.setIndex(itemIndex++);
			this.items.add(item);
			return self();
		}

		public T addParam(MenuItemParamDto param) {
			this.params.add(param);
			return self();
		}

		public MenuItemDto build() {
			return new MenuItemDto(this);
		}
	}

	/**
	 * 
	 * @author robi
	 *
	 */
	protected static class Builder2 extends Builder<Builder2> {
		@Override
		protected Builder2 self() {
			return this;
		}
	}

	public static Builder<?> builder() {
		return new Builder2();
	}
}
