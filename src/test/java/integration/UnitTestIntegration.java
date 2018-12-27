package integration;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.ceiba.parking.domain.Response;
import com.ceiba.parking.domain.Vehicle;

@RunWith(SpringRunner.class)
public class UnitTestIntegration {

	private TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String FIND_ALL_VEHICLES_ENDPOINT = "http://localhost:8090/vehicles/find-all";
	
	private static final String ENTER_VEHICLE_ENDPOINT = "http://localhost:8090/vehicles";
	
	@Test
	public void listAllVehiclesTest() {
		
		// Act
		ResponseEntity<String> parqueaderoResponse = restTemplate.getForEntity(FIND_ALL_VEHICLES_ENDPOINT, String.class);

		// Assert
		assertFalse(parqueaderoResponse.getBody().isEmpty());
	}
	
	@Test
	public void enterVehicleTest() {
		
		Vehicle vehicle = new Vehicle(1, "18/12/2018 01:21:11 PM", "FFF", 3000, "");
		
		// Act
		ResponseEntity<Response<Vehicle>> parqueaderoResponse = restTemplate.postForEntity(ENTER_VEHICLE_ENDPOINT, vehicle, Response<Vehicle>);

		// Assert
		assertNotNull(parqueaderoResponse);
	}
}
