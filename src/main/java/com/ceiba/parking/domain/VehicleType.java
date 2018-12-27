package com.ceiba.parking.domain;

public class VehicleType {

	private int code;
	private String description;
	private int maxNumberOfVehicles;
	
	public int getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	public VehicleType() {}
	
	public VehicleType(int code, String description, int maxNumberOfVehicles) {
		super();
		this.code = code;
		this.description = description;
		this.maxNumberOfVehicles = maxNumberOfVehicles;
	}
	public int getMaxNumberOfVehicles() {
		return maxNumberOfVehicles;
	}
	
	
	
}
