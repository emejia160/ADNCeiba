package com.ceiba.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.VehicleType;
import com.ceiba.parking.repository.VehicleTypeRepository;

@RestController
public class VehicleTypeController {

	@Autowired
	VehicleTypeRepository vehicleTypeRepository;
	
	@RequestMapping("/vehicleTypes/find-all")
	public List<VehicleType> listVehicles(){
		return vehicleTypeRepository.findAll();
	}
	
	@RequestMapping(value = "/vehicleTypes", method=RequestMethod.POST)
	public VehicleType registerType(@RequestBody VehicleType type) {
		return vehicleTypeRepository.save(type);
	}
	
	@RequestMapping(value = "/vehicleTypes/delete-all", method=RequestMethod.DELETE)
	public Response deleteTypes() {
		vehicleTypeRepository.deleteAll();
		return new Response(200, "Vehicles types deleted successfully");
	}
}
