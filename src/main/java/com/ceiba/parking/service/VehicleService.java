package com.ceiba.parking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ceiba.parking.domain.Constants;
import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.domain.VehiclePaymentInfo;
import com.ceiba.parking.domain.VehicleType;
import com.ceiba.parking.repository.VehicleRepository;
import com.ceiba.parking.repository.VehicleTypeRepository;
import com.ceiba.parking.utils.DateUtil;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	VehicleTypeRepository vehicleTypeRepository;
	
	public Response<List<Vehicle>> listAllVehicles(){
		List<Vehicle> vehicles = vehicleRepository.findAll();
		
		if (vehicles.size() > 0) {
			return new Response<List<Vehicle>>(Constants.STATUS_CODE_SUCCESS, Constants.SUCCESS, vehicles);
		} else {
			return new Response<List<Vehicle>>(Constants.STATUS_CODE_SUCCESS, Constants.NO_VEHICLES_IN_PARKING);
		}
		
	}
	
	public Response<?> enterVehicle(@RequestBody Vehicle vehicle) {
		
		if (vehicleRepository.findByLicencePlate(vehicle.getLicencePlate()) != null) {
			return new Response<Object>(Constants.STATUS_CODE_SUCCESS, Constants.VEHICLE_ALREADY_IN_PARKING);
		} 
		
		String validationMessage = validateNumMaxVehicles(vehicle.getType());
		if (validationMessage != "") {
			return new Response<Object>(Constants.STATUS_CODE_SUCCESS, validationMessage);
		}
		
		if (!vehicleCanEnterInParking(vehicle.getLicencePlate())) {
			return new Response<Object>(Constants.STATUS_CODE_SUCCESS, Constants.VEHICLE_CANNOT_ENTER);
		}
		
	    vehicle.setDateIn(DateUtil.getCurrentDateAndTime());
		vehicleRepository.save(vehicle);
		return new Response<Object>(Constants.STATUS_CODE_SUCCESS, Constants.VEHICLE_ENTERED);
		
	}
	
	public Response<?> deleteVehicles() {
		vehicleRepository.deleteAll();
		return new Response<Object>(Constants.STATUS_CODE_SUCCESS, Constants.VEHICLE_DELETED);
	}
	
	public Response<VehiclePaymentInfo> retireVehicle(@RequestParam(value="plate") String licencePlate) {
		
		if (vehicleRepository.findByLicencePlate(licencePlate) == null) {
			return new Response<VehiclePaymentInfo>(Constants.STATUS_CODE_SUCCESS, Constants.VEHICLE_NOT_IN_PARKING);
		} 
		VehiclePaymentInfo paymentInfo = calculateVehicleExitInfo(licencePlate);
		vehicleRepository.deleteByLicencePlate(licencePlate);
		return new Response<VehiclePaymentInfo>(Constants.STATUS_CODE_SUCCESS, Constants.VEHICLE_DELETED , paymentInfo);
	}
	
	public Response<Vehicle> findVehicleByPlate(@RequestParam(value="plate") String licencePlate) {
		
		Vehicle vehicle = vehicleRepository.findByLicencePlate(licencePlate);
		
		if (vehicle == null) {
			return new Response<Vehicle>(Constants.STATUS_CODE_SUCCESS, Constants.VEHICLE_NOT_IN_PARKING);
		}
		return new Response<Vehicle>(Constants.STATUS_CODE_SUCCESS, Constants.SUCCESS, vehicle);		
	}
	
	private String validateNumMaxVehicles(int type) {
		
		int maxNumberMaxOfVehicles = getMaxNumberOfVehiclesPerType(type);
		
		if (vehicleRepository.findByType(type).size() >= maxNumberMaxOfVehicles) {
			return Constants.MAX_NUMBER_VEHICLES + maxNumberMaxOfVehicles;
		}
		return "";
	}
	
	private int getMaxNumberOfVehiclesPerType(int type) {
		
		VehicleType vehicleType = vehicleTypeRepository.findByCode(type);
		
		return (vehicleType != null ? vehicleType.getMaxNumberOfVehicles() : 0);
	}
	
	private boolean vehicleCanEnterInParking(String vehiclePlate) {
		
		if (vehiclePlate.toUpperCase().substring(0, 1).equals(Constants.VEHICLE_START_LETTER)) {
			return (DateUtil.getCurrentDayOfWeek() == 1 || DateUtil.getCurrentDayOfWeek() == 2);
		}
		
		return true;
	}
	
	private VehiclePaymentInfo calculateVehicleExitInfo(String vehiclePlate) {

		Vehicle vehicleToRetire = vehicleRepository.findByLicencePlate(vehiclePlate);
		
		long totalAmount = 0;		
		long remainingHoursPrice = 0;
		long totalHours = calculateTotalHours(vehicleToRetire.getDateIn());
		
		if (totalHours < Constants.HOURS_BEING_DAY) {
			
			totalAmount = (totalHours * vehicleToRetire.getHourPrice());
			
		} else if (totalHours > Constants.HOURS_BEING_DAY && totalHours < 24) {
			
			totalAmount = vehicleToRetire.getDayPrice();
			
		}else {
			
			int daysToPay = Math.toIntExact(totalHours / 24);
			int hoursRemaining = Math.toIntExact(totalHours - (24 * daysToPay));
			
			if (hoursRemaining < Constants.HOURS_BEING_DAY) {
				remainingHoursPrice = (hoursRemaining * vehicleToRetire.getHourPrice());
			} else {
				remainingHoursPrice = vehicleToRetire.getDayPrice();
			}
		}
		
		totalAmount = totalAmount + remainingHoursPrice;
		
		if (vehicleToRetire.getType() == 2 && vehicleToRetire.getNumberOfCC() > Constants.MOTORCYCLE_CC) {
			totalAmount = totalAmount + Constants.MOTORCYCLE_EXTRA_PRICE;
		}
		
		return new VehiclePaymentInfo(totalAmount, totalHours + " Hour(s)");

	}
	
	private long calculateTotalHours(String dateIn) {
		
		Date date2 = null;
		Date date1 = null;
		
		SimpleDateFormat myFormat = new SimpleDateFormat(Constants.DATE_FORMAT_ddMMyyy);
		String inputString1 = dateIn;
		String inputString2 = DateUtil.getCurrentDateAndTime();
		long diff = 0;
		try {
			 date1 = myFormat.parse(inputString1);
			 date2 = myFormat.parse(inputString2);
			 diff = date2.getTime() - date1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) + 1;
	}
}
