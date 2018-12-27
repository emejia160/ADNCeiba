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
	
	
}
