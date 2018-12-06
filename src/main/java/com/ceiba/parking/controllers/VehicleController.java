package com.ceiba.parking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.service.VehicleService;

@RestController
public class VehicleController {

	@Autowired
	VehicleService vehicleService;
	
	
	@RequestMapping("/vehicles/find-all")
	public Response<?> listVehicles(){
		return vehicleService.listAllVehicles();
	}
	
	@RequestMapping(value = "/vehicles", method=RequestMethod.POST)
	public Response<?> enterVehicle(@RequestBody Vehicle vehicle) {
		return vehicleService.enterVehicle(vehicle);
	}
	
	@RequestMapping(value = "/vehicles/delete-all", method=RequestMethod.DELETE)
	public Response<?> deleteVehicles() {
		return vehicleService.deleteVehicles();
	}
	
	@RequestMapping(value = "/vehicles/retire", method=RequestMethod.GET)
	public Response<?> retireVehicle(@RequestParam(value="plate") String licencePlate) {
		return vehicleService.retireVehicle(licencePlate);
	}
	
	@RequestMapping(value = "/vehicles", method=RequestMethod.GET)
	public Response<?> findVehicleByPlate(@RequestParam(value="plate") String licencePlate) {
		return vehicleService.findVehicleByPlate(licencePlate);		
	}
}
