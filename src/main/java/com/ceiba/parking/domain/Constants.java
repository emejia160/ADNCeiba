package com.ceiba.parking.domain;

public class Constants {

	public Constants() {
		
	}
	
	public static final int STATUS_CODE_SUCCESS = 200;
	public static final int STATUS_CODE_FAILURE = 205;
	public static final int CAR_DAY_PRICE = 8000;
	public static final int CAR_HOUR_PRICE = 1000;
	public static final int MOTORCYCLE_DAY_PRICE = 4000;
	public static final int MOTORCYCLE_HOUR_PRICE = 500;
	public static final int MOTORCYCLE_EXTRA_PRICE = 2000;
	public static final int MOTORCYCLE_CC = 500;
	public static final int HOURS_BEING_DAY = 9;
	
	public static final String SUCCESS = "Success!";
	public static final String NO_VEHICLES_IN_PARKING = "There are not vehicles in the parking";
	public static final String VEHICLE_ALREADY_IN_PARKING = "This vehicle is already in the parking!";
	public static final String VEHICLE_CANNOT_ENTER = "This vehicle can only enter in the parking the days Sunday and Monday";
	public static final String VEHICLE_ENTERED = "Vehicle entered successfully";
	public static final String VEHICLE_DELETED = "Vehicle deleted successfully";
	
	public static final String VEHICLE_NOT_IN_PARKING = "The vehicle is not in the parking!";
	public static final String DATE_FORMAT_ddMMyyy = "dd/MM/yyyy hh:mm:ss a";
	public static final String MAX_NUMBER_VEHICLES = "The max number of vehicles in the parking for this type is ";
	public static final String VEHICLE_START_LETTER = "A";
	
}
