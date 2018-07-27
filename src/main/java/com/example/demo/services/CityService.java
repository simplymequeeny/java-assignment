package com.example.demo.services;

import java.util.List;

import com.example.demo.exceptions.CityNotFoundException;
import com.example.demo.models.City;
import com.example.demo.utils.Distance;

public interface CityService {
	City save(City city);
	void delete(Long id);
	City findOne(Long id) throws CityNotFoundException;
	List<City> findAll();
	Boolean exists(Long id);
	Double calculateDistance(Long cityId1, Long cityId2) 
			throws CityNotFoundException;
	Double calculateDistance(Long cityId1, Long cityId2, Distance.Unit unit) 
			throws CityNotFoundException;
}
