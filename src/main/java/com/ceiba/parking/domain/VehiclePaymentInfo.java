package com.ceiba.parking.domain;

public class VehiclePaymentInfo {

	private long totalAmountToPay;
	private String totalTimeInParking;
		
	public VehiclePaymentInfo(long totalAmountToPay, String totalTimeInParking) {
		super();
		this.setTotalAmountToPay(totalAmountToPay);
		this.setTotalTimeInParking(totalTimeInParking);
	}
	
	public VehiclePaymentInfo() {
		
	}

	public long getTotalAmountToPay() {
		return totalAmountToPay;
	}

	public void setTotalAmountToPay(long totalAmountToPay) {
		this.totalAmountToPay = totalAmountToPay;
	}

	public String getTotalTimeInParking() {
		return totalTimeInParking;
	}

	public void setTotalTimeInParking(String totalTimeInParking) {
		this.totalTimeInParking = totalTimeInParking;
	}
	
	
}
