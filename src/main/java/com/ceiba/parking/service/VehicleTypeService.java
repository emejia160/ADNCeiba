package com.ceiba.parking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.domain.Constants;
import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.VehicleType;
import com.ceiba.parking.repository.VehicleTypeRepository;

@Service
public class VehicleTypeService {

	@Autowired
	VehicleTypeRepository vehicleTypeRepository;
	
	public Response<List<VehicleType>> listAllVehicleTypes(){
		List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
		
		if (vehicleTypes.size() > 0) {
			return new Response<List<VehicleType>>(Constants.STATUS_CODE_SUCCESS, Constants.SUCCESS, vehicleTypes);
		} else {
			return new Response<List<VehicleType>>(Constants.STATUS_CODE_SUCCESS, Constants.NO_VEHICLES_IN_PARKING);
		}
		
	}
	
	public Response<Object> deleteAll(){
		vehicleTypeRepository.deleteAll();
		return new Response<Object>(200, "Vehicles types deleted successfully");
		
	}
	
	public VehicleType enterType(VehicleType type){
		return vehicleTypeRepository.save(type);
		
	}
	
}
