package com.ceiba.parking.unit;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ceiba.parking.domain.Constants;
import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.domain.VehiclePaymentInfo;
import com.ceiba.parking.domain.VehicleType;
import com.ceiba.parking.repository.VehicleRepository;
import com.ceiba.parking.repository.VehicleTypeRepository;
import com.ceiba.parking.service.ParkingTicketService;
import com.ceiba.parking.service.VehicleService;
import com.ceiba.parking.service.VehicleTypeService;
import com.ceiba.parking.utils.DateUtil;

import Exception.VehicleNotInParking;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class UnitTest {

	private static final long EXPECTED_PAYMENT_CAR_HOUR = 1000;
	private static final long EXPECTED_PAYMENT_MOTO_HOUR = 500;
	private static final long EXPECTED_PAYMENT_MOTO_500_HOUR = 2500;
	
	private static final long EXPECTED_PAYMENT_CAR_DAY = 8000;
	private static final long EXPECTED_PAYMENT_MOTO_DAY = 4000;
	private static final long EXPECTED_PAYMENT_MOTO_500_DAY = 6000;
	
	private static final long EXPECTED_PAYMENT_CAR_2_DAY = 16000;
	private static final long EXPECTED_PAYMENT_MOTO_2_DAY = 8000;
	private static final long EXPECTED_PAYMENT_MOTO_500_2_DAY = 10000;
	
	private static final long EXPECTED_PAYMENT_CAR_28_HOURS = 12000;
	private static final long EXPECTED_PAYMENT_MOTO_28_HOURS = 6000;
	private static final long EXPECTED_PAYMENT_MOTO_500_28_HOURS = 8000;
	
	@InjectMocks
	VehicleService vehicleServiceImpl;
	
	@InjectMocks
	ParkingTicketService parkingTicketServiceImpl;
	
	@InjectMocks
	VehicleTypeService vehicleTypeServiceImpl;
	
	@Mock
	VehicleRepository vehicleRepository;
	
	@Mock
	VehicleTypeRepository vehicleTypeRepository;
	
	@Test
	public void testFindAllVehicles() {
		
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicles.add(new Vehicle(1, "18/12/2018 01:21:11 PM", "FFF", 3000, ""));
		when(vehicleTypeRepository.findByCode(1)).thenReturn(new VehicleType(1, "Carro", 10));
		when(vehicleRepository.findAll()).thenReturn(vehicles);
		
		Response<List<Vehicle>> response = vehicleServiceImpl.listAllVehicles();
		
		assertNotNull(response.getData());
	}
	
	@Test
	public void testGetNameDescriptionPerType() {
		
		when(vehicleTypeRepository.findByCode(1)).thenReturn(new VehicleType(1, "Carro", 10));
		
		String response = vehicleServiceImpl.getNameVehiclePerType(1);
		assertNotNull(response);
	}
	
	@Test
	public void testSetNameDescriptionPerType() {
		
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicles.add(new Vehicle(1, "18/12/2018 01:21:11 PM", "FFF", 3000, ""));
		when(vehicleTypeRepository.findByCode(1)).thenReturn(new VehicleType(1, "Carro", 10));
		
		List<Vehicle> response = vehicleServiceImpl.setTypeDescriptionForVehicles(vehicles);
		assertNotNull(response);
	}
	
	@Test
	public void testFindAllVehiclesEmpty() {
		
		when(vehicleRepository.findAll()).thenReturn(new ArrayList<Vehicle>());
		Response<List<Vehicle>> response = vehicleServiceImpl.listAllVehicles();
		
		
		assertNull(response.getData());
	}
	
	@Test
	public void testFindAllVehicleTypes() {
		
		List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
		vehicleTypes.add(new VehicleType());
		when(vehicleTypeRepository.findAll()).thenReturn(vehicleTypes);
		Response<List<VehicleType>> response = vehicleTypeServiceImpl.listAllVehicleTypes();
		
		assertNotNull(response.getData());
	}
	
	@Test
	public void testFindAllVehicleTypesempty() {
		
		when(vehicleTypeRepository.findAll()).thenReturn(new ArrayList<VehicleType>());
		Response<List<VehicleType>> response = vehicleTypeServiceImpl.listAllVehicleTypes();
		
		assertNull(response.getData());
	}
	
	@Test
	public void testDeleteAllVehicleTypes() {
		
		Response<Object> response = vehicleTypeServiceImpl.deleteAll();
		
		assertNotNull(response);
	}
	
	@Test
	public void testEnterVehicleTypes() {
		
		VehicleType vehicleType = vehicleTypeServiceImpl.enterType(new VehicleType());
		
		assertNull(vehicleType);
	}
	
	@Test
	public void validateVehicleCanPark() {
		
		boolean response = vehicleServiceImpl.vehicleCanEnterInParking(Constants.VEHICLE_PLATE_START_WITHOUT_A);
		
		assertTrue(response);
		
	}
	
	@Test
	public void validateVehicleCanNotPark() {
		
		boolean response = vehicleServiceImpl.vehicleCanEnterInParking(Constants.VEHICLE_PLATE_START_WITH_A);
		
		assertFalse(response);
		
	}
	
	@Test
	public void validateCarMaxInParking() {
		
		when(vehicleTypeRepository.findByCode(1)).thenReturn(new VehicleType(1, "Carro", 10));
		
		
		boolean response = vehicleServiceImpl.validateNumMaxVehiclesPerType(1);
		assertTrue(response);
		
	}
	
	@Test
	public void validateMotorcycleMaxInParking() {
		
		when(vehicleTypeRepository.findByCode(2)).thenReturn(new VehicleType(2, "Moto", 10));
		
		boolean response = vehicleServiceImpl.validateNumMaxVehiclesPerType(2);
		assertTrue(response);
		
	}
	
	@Test
	public void validateMotorcycleNotMaxInParking() {
		
		when(vehicleTypeRepository.findByCode(1)).thenReturn(new VehicleType(2, "Moto", 10));
		
		boolean response = vehicleServiceImpl.validateNumMaxVehiclesPerType(2);
		assertFalse(response);
		
	}
	
	
	@Test
	public void getMaxNumberOfVehiclesPerType() {
		
		when(vehicleTypeRepository.findByCode(1)).thenReturn(new VehicleType(1, "Carro", 10));
		
		
		int response = vehicleServiceImpl.getMaxNumberOfVehiclesPerType(1);
		assertEquals(response, 10);
		
	}
	
	
	@Test
	public void validateVehicleIsInParking() {
		
		when(vehicleRepository.findByLicencePlate("ASD")).thenReturn(new Vehicle());
		
		Response<Vehicle> response = vehicleServiceImpl.findVehicleByPlate("ASD");
		assertNotNull(response.getData());
		
	}
	
	@Test
	public void validateVehicleNotInParking() {
		
		when(vehicleRepository.findByLicencePlate("ASD")).thenReturn(null);
		
		Response<Vehicle> response = vehicleServiceImpl.findVehicleByPlate("ASD");
		assertNull(response.getData());
		
	}
	
	@Test(expected = VehicleNotInParking.class)
	public void retireVehicleNoInParking() throws VehicleNotInParking {
		
		when(vehicleRepository.findByLicencePlate("ASD")).thenReturn(null);
		
	}
	
	@Test
	public void retireVehicleInParking() throws VehicleNotInParking {
		
		Vehicle vehicle = new Vehicle(1, "18/12/2018 01:21:11 PM", "FFF", 3000, "");
		when(vehicleRepository.findByLicencePlate("ASD")).thenReturn(vehicle);
		when(parkingTicketServiceImpl.calculateVehicleExitInfo(vehicle)).thenReturn(new VehiclePaymentInfo());
		
		VehiclePaymentInfo response = vehicleServiceImpl.retireVehicle("ASD");
		
		assertNotNull(response);
		
	}
	
	@Test
	public void deleteVehicles() {
		
		Response<?> response = vehicleServiceImpl.deleteVehicles();
		
		assertNotNull(response);
		
	}
	
	@Test
	public void validatePaymentCar1Hour() {
		
		Vehicle vehicle = new Vehicle(1, "", "FFF", 3000, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(1, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_CAR_HOUR, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleNo500CC1Hour() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 300, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(1, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_HOUR, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleWith500CC1Hour() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 700, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(1, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_500_HOUR, amount);
		
	}
	
	@Test
	public void validatePaymentCar1Day() {
		
		Vehicle vehicle = new Vehicle(1, "", "FFF", 3000, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(16, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_CAR_DAY, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleNo500CC1Day() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 300, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(16, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_DAY, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleWith500CC1Day() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 700, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(16, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_500_DAY, amount);
		
	}
	
	@Test
	public void validatePaymentCar2Days() {
		
		Vehicle vehicle = new Vehicle(1, "", "FFF", 3000, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(36, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_CAR_2_DAY, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleNo500CC2Days() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 300, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(36, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_2_DAY, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleWith500CC2Days() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 700, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(36, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_500_2_DAY, amount);
		
	}
	
	
	@Test
	public void validatePaymentCar28Hours() {
		
		Vehicle vehicle = new Vehicle(1, "", "FFF", 3000, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(28, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_CAR_28_HOURS, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleNo500CC28Hours() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 300, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(28, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_28_HOURS, amount);
		
	}
	
	@Test
	public void validatePaymentMotorcycleWith500CC28Hours() {
		
		Vehicle vehicle = new Vehicle(2, "", "FFF", 700, "");
		long amount = parkingTicketServiceImpl.calculateTotalAmountToPay(28, vehicle);
		
		assertEquals(EXPECTED_PAYMENT_MOTO_500_28_HOURS, amount);
		
	}
	
	
	@Test
	public void validateTotalHoursInParking() {
		
		long hoursExpected = 1;
		String dateMock = DateUtil.getCurrentDateAndTime();
		
		long totalHours = parkingTicketServiceImpl.calculateTotalHours(dateMock);
		
		assertEquals(totalHours, hoursExpected);
		
	}
	
	
	
}
