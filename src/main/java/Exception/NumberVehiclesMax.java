package Exception;

import com.ceiba.parking.domain.Constants;

public class NumberVehiclesMax extends Exception{

	public NumberVehiclesMax(){
		super(Constants.MAX_NUMBER_VEHICLES);
	}
}
