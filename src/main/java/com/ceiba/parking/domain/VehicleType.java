package com.ceiba.parking.domain;

public class VehicleType {

	private int code;
	private String description;
	private int maxNumberOfVehicles;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public VehicleType(int code, String description, int maxNumberOfVehicles) {
		super();
		this.code = code;
		this.description = description;
		this.maxNumberOfVehicles = maxNumberOfVehicles;
	}
	public int getMaxNumberOfVehicles() {
		return maxNumberOfVehicles;
	}
	public void setMaxNumberOfVehicles(int maxNumberOfVehicles) {
		this.maxNumberOfVehicles = maxNumberOfVehicles;
	}
	
	
	
}
