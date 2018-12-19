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

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public void setNumberOfCC(int numberOfCC) {
		this.numberOfCC = numberOfCC;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", type=" + type + ", licencePlate=" + licencePlate + ", dateIn=" + dateIn + "]";
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

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	
	
}
