package Exception;

import com.ceiba.parking.domain.Constants;

public class VehicleAlreadyInParking extends Exception{
	public VehicleAlreadyInParking(){
		super(Constants.VEHICLE_ALREADY_IN_PARKING);
	}
}
