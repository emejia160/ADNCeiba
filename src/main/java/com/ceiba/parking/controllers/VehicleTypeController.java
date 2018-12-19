package com.ceiba.parking.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.VehicleType;
import com.ceiba.parking.service.VehicleTypeService;

@RestController
public class VehicleTypeController {

	@Autowired
	VehicleTypeService vehicleTypeService;
	
	@RequestMapping("/vehicleTypes/find-all")
	public Response<?> listVehicles(){
		return vehicleTypeService.listAllVehicleTypes();
	}
	
	@RequestMapping(value = "/vehicleTypes", method=RequestMethod.POST)
	public VehicleType registerType(@RequestBody VehicleType type) {
		return vehicleTypeService.enterType(type);
	}
	
	@RequestMapping(value = "/vehicleTypes/delete-all", method=RequestMethod.DELETE)
	public Response<?> deleteTypes() {
		return vehicleTypeService.deleteAll();
	}
}
