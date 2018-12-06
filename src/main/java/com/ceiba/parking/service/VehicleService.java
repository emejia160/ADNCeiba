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
	
	public Response listAllVehicles(){
		List<Vehicle> vehicles = vehicleRepository.findAll();
		
		if (vehicles.size() > 0) {
			return new Response(200, "Success!", vehicles);
		} else {
			return new Response(200, "There are not vehicles in the parking");
		}
		
	}
	
	public Response enterVehicle(@RequestBody Vehicle vehicle) {
		
		if (vehicleRepository.findByLicencePlate(vehicle.getLicencePlate()) != null) {
			return new Response(200, "Vehicle already in the parking!");
		} 
		
		String validationMessage = validateNumMaxVehicles(vehicle.getType());
		if (validationMessage != "") {
			return new Response(200, validationMessage);
		}
		
		if (!vehicleCanEnterInParking(vehicle.getLicencePlate())) {
			return new Response(200, "This vehicle can only enter in the parking the days Sunday and Monday");
		}
		
	    vehicle.setDateIn(DateUtil.getCurrentDateAndTime());
		vehicleRepository.save(vehicle);
		return new Response(200, "Vehicle entered successfully");
		
	}
	
	public Response deleteVehicles() {
		vehicleRepository.deleteAll();
		return new Response(200, "Vehicles deleted successfully");
	}
	
	public Response retireVehicle(@RequestParam(value="plate") String licencePlate) {
		
		if (vehicleRepository.findByLicencePlate(licencePlate) == null) {
			return new Response(200, "Vehicle is not in the parking!");
		} 
		VehiclePaymentInfo paymentInfo = calculateVehicleExitInfo(licencePlate);
		vehicleRepository.deleteByLicencePlate(licencePlate);
		return new Response(200, "Vehicle retired successfully!", paymentInfo);
	}
	
	public Response findVehicleByPlate(@RequestParam(value="plate") String licencePlate) {
		
		Vehicle vehicle = vehicleRepository.findByLicencePlate(licencePlate);
		
		if (vehicle == null) {
			return new Response(200, "Vehicle is not in the parking!");
		}
		return new Response(200, "Success!", vehicle);		
	}
	
	private String validateNumMaxVehicles(int type) {
		
		int maxNumberMaxOfVehicles = getMaxNumberOfVehiclesPerType(type);
		
		if (vehicleRepository.findByType(type).size() >= maxNumberMaxOfVehicles) {
			return "The max number of vehicles in the parking for this type is " + maxNumberMaxOfVehicles;
		}
		return "";
	}
	
	private int getMaxNumberOfVehiclesPerType(int type) {
		
		VehicleType vehicleType = vehicleTypeRepository.findByCode(type);
		
		return (vehicleType != null ? vehicleType.getMaxNumberOfVehicles() : 0);
	}
	
	private boolean vehicleCanEnterInParking(String vehiclePlate) {
		
		if (vehiclePlate.toUpperCase().substring(0, 1).equals("A")) {
			return (DateUtil.getCurrentDayOfWeek() == 1 || DateUtil.getCurrentDayOfWeek() == 2);
		}
		
		return true;
	}
	
	private VehiclePaymentInfo calculateVehicleExitInfo(String vehiclePlate) {

		Vehicle vehicleToRetire = vehicleRepository.findByLicencePlate(vehiclePlate);
		
		long totalAmount = 0;		
		long remainingHoursPrice = 0;
		long totalHours = calculateTotalHours(vehicleToRetire.getDateIn());
		
		if (totalHours < 9) {
			
			totalAmount = (totalHours * vehicleToRetire.getHourPrice());
			
		} else if (totalHours > 9 && totalHours < 24) {
			
			totalAmount = vehicleToRetire.getDayPrice();
			
		}else {
			
			int daysToPay = Math.toIntExact(totalHours / 24);
			int hoursRemaining = Math.toIntExact(totalHours - (24 * daysToPay));
			
			if (hoursRemaining < 9) {
				remainingHoursPrice = (hoursRemaining * vehicleToRetire.getHourPrice());
			} else {
				remainingHoursPrice = vehicleToRetire.getDayPrice();
			}
		}
		
		totalAmount = totalAmount + remainingHoursPrice;
		
		if (vehicleToRetire.getType() == 2 && vehicleToRetire.getNumberOfCC() > 500) {
			totalAmount = totalAmount + 2000;
		}
		
		return new VehiclePaymentInfo(totalAmount, totalHours + " Hour(s)");

	}
	
	private long calculateTotalHours(String dateIn) {
		
		Date date2 = null;
		Date date1 = null;
		
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
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
