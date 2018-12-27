package Exception;

import com.ceiba.parking.domain.Constants;

public class PlateWithException extends Exception{
	
	public PlateWithException(){
		super(Constants.VEHICLE_CANNOT_ENTER);
	}
}
