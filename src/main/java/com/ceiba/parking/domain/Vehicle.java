package com.ceiba.parking.domain;

import org.springframework.data.annotation.Id;

public class Vehicle {

	@Id
	private String id;
	private int type;
	private String licencePlate;
	private String dateIn;
	private String typeDescription;
	private int numberOfCC;
	
	public Vehicle() {
		
	}
	
	public Vehicle(int type, String dateIn, String licencePlate, int numberOfCC, String typeDescription) {
		this.type = type;
		this.dateIn = dateIn;
		this.licencePlate = licencePlate;
		this.numberOfCC = numberOfCC;
		this.typeDescription = typeDescription;
	}

	public String getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public String getDateIn() {
		return dateIn;
	}

	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}
	
	public int getNumberOfCC() {
		return numberOfCC;
	}
	
	public long getHourPrice() {
		if (this.type == 1) {
			return Constants.CAR_HOUR_PRICE;
		} else {
			return Constants.MOTORCYCLE_HOUR_PRICE;
		}
	}
	
	public long getDayPrice() {
		if (this.type == 1) {
			return Constants.CAR_DAY_PRICE;
		} else {
			return Constants.MOTORCYCLE_DAY_PRICE;
		}
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	
	
}
