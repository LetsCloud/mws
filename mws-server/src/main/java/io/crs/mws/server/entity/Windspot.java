/**
 * 
 */
package io.crs.mws.server.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * @author robi
 *
 */
@Entity
public class Windspot extends BaseEntity {

	/**
	 * Windspot neve
	 */
	@Index
	private String name;

	private String coordinateX;

	private String coordinateY;

	private String coordinateZ;

	public Windspot() {
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

	@Override
	public String toString() {
		return "Windspot [name=" + name + ", coordinateX=" + coordinateX + ", coordinateY=" + coordinateY
				+ ", coordinateZ=" + coordinateZ + "]";
	}

}
