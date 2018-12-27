package Exception;

import com.ceiba.parking.domain.Constants;

public class VehicleNotInParking extends Exception{
	public VehicleNotInParking(){
		super(Constants.VEHICLE_NOT_IN_PARKING);
	}
}
