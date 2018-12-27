package com.ceiba.parking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.domain.Constants;
import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.domain.VehiclePaymentInfo;
import com.ceiba.parking.domain.VehicleType;
import com.ceiba.parking.repository.VehicleRepository;
import com.ceiba.parking.repository.VehicleTypeRepository;
import com.ceiba.parking.utils.DateUtil;

import Exception.NumberVehiclesMax;
import Exception.PlateWithException;
import Exception.VehicleAlreadyInParking;
import Exception.VehicleNotInParking;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	ParkingTicketService parkingTicketService;

	@Autowired
	VehicleTypeRepository vehicleTypeRepository;
	
	public Response<List<Vehicle>> listAllVehicles(){
		List<Vehicle> vehicles = vehicleRepository.findAll();
		
		if (vehicles.size() > 0) {
			return new Response<List<Vehicle>>(Constants.SUCCESS, setTypeDescriptionForVehicles(vehicles));
		} else {
			return new Response<List<Vehicle>>(Constants.NO_VEHICLES_IN_PARKING);
		}
	}
	
	public Vehicle enterVehicle(Vehicle vehicle) throws NumberVehiclesMax, PlateWithException, VehicleAlreadyInParking{
		
		if (vehicleRepository.findByLicencePlate(vehicle.getLicencePlate()) != null) {
			throw new VehicleAlreadyInParking();
		} 
		
		boolean validationMessage = validateNumMaxVehiclesPerType(vehicle.getType());
		if (validationMessage == false) {
			throw new NumberVehiclesMax();
		}
		
		if (!vehicleCanEnterInParking(vehicle.getLicencePlate())) {

			throw new PlateWithException();
		}
		
	    vehicle.setDateIn(DateUtil.getCurrentDateAndTime());
		vehicleRepository.save(vehicle);
		
		return vehicle;
	}
	
	public Response<?> deleteVehicles() {
		vehicleRepository.deleteAll();
		return new Response<Object>(Constants.VEHICLE_DELETED);
	}
	
	public VehiclePaymentInfo retireVehicle(String licencePlate) throws VehicleNotInParking{
		
		Vehicle vehicleToRetire = vehicleRepository.findByLicencePlate(licencePlate);
		
		if (vehicleToRetire == null) {
			throw new VehicleNotInParking();
		} 
		VehiclePaymentInfo paymentInfo = parkingTicketService.calculateVehicleExitInfo(vehicleToRetire);
		vehicleRepository.deleteByLicencePlate(licencePlate);
		return paymentInfo;
	}
	
	public Response<Vehicle> findVehicleByPlate(String licencePlate) {
		
		Vehicle vehicle = vehicleRepository.findByLicencePlate(licencePlate);
		
		if (vehicle == null) {
			return new Response<Vehicle>(Constants.VEHICLE_NOT_IN_PARKING);
		}
		return new Response<Vehicle>(Constants.SUCCESS, vehicle);		
	}
	
	public boolean validateNumMaxVehiclesPerType(int type) {
		
		int maxNumberMaxOfVehicles = getMaxNumberOfVehiclesPerType(type);
		
		if (vehicleRepository.findByType(type).size() >= maxNumberMaxOfVehicles) {
			return false;
		}
		return true;
	}
	
	public int getMaxNumberOfVehiclesPerType(int type) {
		
		VehicleType vehicleType = vehicleTypeRepository.findByCode(type);
		
		return (vehicleType != null ? vehicleType.getMaxNumberOfVehicles() : 0);
	}
	
	public boolean vehicleCanEnterInParking(String vehiclePlate) {
		
		if (vehiclePlate.toUpperCase().substring(0, 1).equals(Constants.VEHICLE_START_LETTER)) {
			return (DateUtil.getCurrentDayOfWeek() == 1 || DateUtil.getCurrentDayOfWeek() == 2);
		}
		
		return true;
	}
	
	public List<Vehicle> setTypeDescriptionForVehicles(List<Vehicle> vehicles){
		
		for(Vehicle vehicle : vehicles) {
			vehicle.setTypeDescription(getNameVehiclePerType(vehicle.getType()));
		}
		
		return vehicles;
	}
	
	public String getNameVehiclePerType(int type) {
		VehicleType vehicleType = vehicleTypeRepository.findByCode(type);
		
		return vehicleType.getDescription();
	}
}
