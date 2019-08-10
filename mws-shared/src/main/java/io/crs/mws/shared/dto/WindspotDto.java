/**
 * 
 */
package io.crs.mws.shared.dto;

/**
 * @author robi
 *
 */
@SuppressWarnings("serial")
public class WindspotDto extends BaseDto {

	private String name;

	private String coordinateX;

	private String coordinateY;

	private String coordinateZ;

	public WindspotDto() {
	}

	public WindspotDto(String name, String coordinateX, String coordinateY, String coordinateZ) {
		this();
		this.name = name;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.coordinateZ = coordinateZ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(String coordinateX) {
		this.coordinateX = coordinateX;
	}

	public String getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY) {
		this.coordinateY = coordinateY;
	}

	public String getCoordinateZ() {
		return coordinateZ;
	}

	public void setCoordinateZ(String coordinateZ) {
		this.coordinateZ = coordinateZ;
	}

}