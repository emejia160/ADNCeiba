package com.ceiba.parking.domain;

public class Response<T> {
	
	private String message;
	private T data;
	
	public Response(String message) {
		this.message = message;
	}
	public Response(String message, T data) {
		this.message = message;
		this.data = data;
	}
	public T getData() {
		return data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setData(T data) {
		this.data = data;
	}

}
