package com.ceiba.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ceiba.parking.domain.Constants;
import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.domain.VehiclePaymentInfo;
import com.ceiba.parking.service.VehicleService;

import Exception.NumberVehiclesMax;
import Exception.PlateWithException;
import Exception.VehicleAlreadyInParking;
import Exception.VehicleNotInParking;

@RestController
public class VehicleController {

	@Autowired
	VehicleService vehicleService;
	
	
	@RequestMapping("/vehicles/find-all")
	public ResponseEntity<Response<List<Vehicle>>> listVehicles(){
		
		Response<List<Vehicle>> response = vehicleService.listAllVehicles();
		return new ResponseEntity<Response<List<Vehicle>>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/vehicles", method=RequestMethod.POST)
	public ResponseEntity<Response<Vehicle>> enterVehicle(@RequestBody Vehicle vehicle) {
		
		Vehicle vehicleRegistred = null;
		try {
			
			vehicleRegistred = vehicleService.enterVehicle(vehicle);
			 
		} catch (NumberVehiclesMax e) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response<Vehicle>(e.getMessage()));
		} catch (PlateWithException e) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response<Vehicle>(e.getMessage()));
		} catch (VehicleAlreadyInParking e) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response<Vehicle>(e.getMessage()));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new Response<Vehicle>(Constants.VEHICLE_ENTERED, vehicleRegistred));
	}
	
	@RequestMapping(value = "/vehicles/delete-all", method=RequestMethod.DELETE)
	public Response<?> deleteVehicles() {
		return vehicleService.deleteVehicles();
	}
	
	@RequestMapping(value = "/vehicles/retire", method=RequestMethod.GET)
	public ResponseEntity<Response<VehiclePaymentInfo>> retireVehicle(@RequestParam(value="plate") String licencePlate) {
		
		VehiclePaymentInfo paymentInfo = null;
		
		try {
			
			paymentInfo = vehicleService.retireVehicle(licencePlate);
			
		} catch (VehicleNotInParking e) {
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response<VehiclePaymentInfo>(e.getMessage()));
			
		}
		
		return new ResponseEntity<Response<VehiclePaymentInfo>>(new Response<VehiclePaymentInfo>(Constants.VEHICLE_DELETED , paymentInfo), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/vehicles", method=RequestMethod.GET)
	public Response<?> findVehicleByPlate(@RequestParam(value="plate") String licencePlate) {
		return vehicleService.findVehicleByPlate(licencePlate);		
	}
}
