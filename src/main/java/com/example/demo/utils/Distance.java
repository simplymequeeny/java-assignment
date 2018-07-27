package com.example.demo.utils;

public interface Distance {
	
	enum Unit {
		MILES, KILOMETERS, NAUTICAL_MILES
	}
	
	@SuppressWarnings("incomplete-switch")
	static double calculate(double lat1, double lon1, double lat2, double lon2, Unit unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + 
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515;
		
		switch (unit) {
		case KILOMETERS:
			dist = dist * 1.609344;
			break;
		case NAUTICAL_MILES:
			dist = dist * 0.8684;
			break;
		}

		return round(dist, 7);
	}
	
	public static double round(double value, int places) {
	    double scale = Math.pow(10, places);
	    return Math.round(value * scale) / scale;
	}
}
