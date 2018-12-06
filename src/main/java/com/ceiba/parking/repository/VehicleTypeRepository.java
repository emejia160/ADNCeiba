package com.ceiba.parking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ceiba.parking.domain.VehicleType;

public interface VehicleTypeRepository extends MongoRepository<VehicleType, String>{

	public VehicleType findByCode(int code);
}
