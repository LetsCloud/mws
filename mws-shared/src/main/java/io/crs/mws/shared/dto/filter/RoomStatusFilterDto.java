/**
 * 
 */
package io.crs.mws.shared.dto.filter;

import java.util.Date;

import io.crs.mws.shared.dto.Dto;

/**
 * @author CR
 *
 */
@SuppressWarnings("serial")
public class RoomStatusFilterDto implements Dto {

	private String hotelKey;

	private Date date;

	private String fromRoom;

	private String toRoom;

	private String fromFloor;

	private String toFloor;

	private Boolean dirtyVacant;

	private Boolean cleanVacant;

	private Boolean okVacant;

	private Boolean dirtyOccupied;

	private Boolean cleanOccupied;

	private Boolean okOccupied;

	private Boolean outOfService;

	private Boolean showRoom;

	public RoomStatusFilterDto() {
		this.cleanOccupied = true;
		this.cleanVacant = true;
		this.dirtyOccupied = true;
		this.dirtyVacant = true;
		this.fromFloor = "";
		this.toFloor = "";
		this.fromRoom = "";
		this.okOccupied = true;
		this.okVacant = true;
		this.outOfService = true;
		this.showRoom = true;
		this.toRoom = "";
	}

	public String getHotelKey() {
		return hotelKey;
	}

	public void setHotelKey(String hotelKey) {
		this.hotelKey = hotelKey;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFromRoom() {
		return fromRoom;
	}

	public void setFromRoom(String fromRoom) {
		if (fromRoom == null)
			fromRoom = "";
		this.fromRoom = fromRoom;
	}

	public String getToRoom() {
		return toRoom;
	}

	public void setToRoom(String toRoom) {
		if (toRoom == null)
			toRoom = "";
		this.toRoom = toRoom;
	}

	public String getFromFloor() {
		return fromFloor;
	}

	public void setFromFloor(String fromFloor) {
		this.fromFloor = fromFloor;
	}

	public String getToFloor() {
		return toFloor;
	}

	public void setToFloor(String toFloor) {
		this.toFloor = toFloor;
	}

	public Boolean getDirtyVacant() {
		return dirtyVacant;
	}

	public void setDirtyVacant(Boolean dirtyVacant) {
		this.dirtyVacant = dirtyVacant;
	}

	public Boolean getCleanVacant() {
		return cleanVacant;
	}

	public void setCleanVacant(Boolean cleanVacant) {
		this.cleanVacant = cleanVacant;
	}

	public Boolean getOkVacant() {
		return okVacant;
	}

	public void setOkVacant(Boolean okVacant) {
		this.okVacant = okVacant;
	}

	public Boolean getDirtyOccupied() {
		return dirtyOccupied;
	}

	public void setDirtyOccupied(Boolean dirtyOccupied) {
		this.dirtyOccupied = dirtyOccupied;
	}

	public Boolean getCleanOccupied() {
		return cleanOccupied;
	}

	public void setCleanOccupied(Boolean cleanOccupied) {
		this.cleanOccupied = cleanOccupied;
	}

	public Boolean getOkOccupied() {
		return okOccupied;
	}

	public void setOkOccupied(Boolean okOccupied) {
		this.okOccupied = okOccupied;
	}

	public Boolean getOutOfService() {
		return outOfService;
	}

	public void setOutOfService(Boolean outOfService) {
		this.outOfService = outOfService;
	}

	public Boolean getShowRoom() {
		return showRoom;
	}

	public void setShowRoom(Boolean showRoom) {
		this.showRoom = showRoom;
	}

	@Override
	public String toString() {
		return "RoomStatusFilterDto[hotelKey=" + this.hotelKey + ", date=" + this.date + "fromRoom=" + this.fromRoom
				+ ", toRoom=" + this.toRoom + ", fromFloor=" + this.fromFloor + ", toFloor=" + this.toFloor;
	}
}
