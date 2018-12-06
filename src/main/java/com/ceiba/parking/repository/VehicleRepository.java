package com.ceiba.parking.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ceiba.parking.domain.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
	
	public Vehicle findByLicencePlate(String licencePlate);
	public void deleteByLicencePlate(String licencePlate);
	public List<Vehicle> findByType(int type);
}
