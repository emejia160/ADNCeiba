package com.ceiba.parking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.domain.Constants;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.domain.VehiclePaymentInfo;
import com.ceiba.parking.repository.VehicleRepository;
import com.ceiba.parking.utils.DateUtil;

@Service
public class ParkingTicketService {

	@Autowired
	VehicleRepository vehicleRepository;
	
	public VehiclePaymentInfo calculateVehicleExitInfo(Vehicle vehicleToRetire) {
		
		long totalHoursInParking = calculateTotalHours(vehicleToRetire.getDateIn());
		long totalAmount = calculateTotalAmountToPay(totalHoursInParking, vehicleToRetire); 
		
		
		return new VehiclePaymentInfo(totalAmount, totalHoursInParking + " Hour(s)");

	}
	
	public long calculateTotalAmountToPay(long totalHoursInParking, Vehicle vehicleToRetire) {
		
		long totalAmount = 0;		
		long remainingHoursPrice = 0;
		
		if (totalHoursInParking < Constants.HOURS_BEING_DAY) {
			
			totalAmount = (totalHoursInParking * vehicleToRetire.getHourPrice());
			
		} else if (totalHoursInParking > Constants.HOURS_BEING_DAY && totalHoursInParking < 24) {
			
			totalAmount = vehicleToRetire.getDayPrice();
			
		}else {
			
			int daysToPay = Math.toIntExact(totalHoursInParking / 24);
			totalAmount = daysToPay * vehicleToRetire.getDayPrice();
			int hoursRemaining = Math.toIntExact(totalHoursInParking - (24 * daysToPay));
			
			if (hoursRemaining < Constants.HOURS_BEING_DAY) {
				remainingHoursPrice = (hoursRemaining * vehicleToRetire.getHourPrice());
			} else {
				remainingHoursPrice = vehicleToRetire.getDayPrice();
			}
		}
		
		totalAmount = totalAmount + remainingHoursPrice;
		
		if (vehicleToRetire.getType() == 2 && vehicleToRetire.getNumberOfCC() > Constants.MOTORCYCLE_CC) {
			totalAmount = totalAmount + Constants.MOTORCYCLE_EXTRA_PRICE;
		}
		
		return totalAmount;
	}
	
	public long calculateTotalHours(String dateIn) {
		
		Date date2 = null;
		Date date1 = null;
		
		SimpleDateFormat myFormat = new SimpleDateFormat(Constants.DATE_FORMAT_ddMMyyy);
		String inputString1 = dateIn;
		String inputString2 = DateUtil.getCurrentDateAndTime();
		long diff = 0;
		try {
			 date1 = myFormat.parse(inputString1);
			 date2 = myFormat.parse(inputString2);
			 diff = date2.getTime() - date1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) + 1;
	}
	
}
