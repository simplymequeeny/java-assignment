package com.example.demo.exceptions;

public class CityNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public CityNotFoundException() {
		super("City not found");
	}
	
	public CityNotFoundException(String message) {
		super(message);
	}
}
