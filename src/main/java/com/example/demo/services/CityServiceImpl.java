package com.example.demo.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.CityNotFoundException;
import com.example.demo.models.City;
import com.example.demo.repositories.CityRepository;
import com.example.demo.utils.Distance;
import com.example.demo.utils.Distance.Unit;

@Service
public class CityServiceImpl implements CityService {

	@Autowired private CityRepository cityRepository;
	
	@Override
	public City save(City city) {
		if (city.getId() == null) city.setId(Calendar.getInstance().getTime().getTime());
		return cityRepository.saveAndFlush(city);
	}

	@Override
	public void delete(Long id) {
		cityRepository.deleteById(id);;
	}
	
	@Override
	public City findOne(Long id) throws CityNotFoundException {
		return cityRepository.findById(id)
				.orElseThrow(() -> new CityNotFoundException(String.format("ID {0} not found", id)));
	}

	@Override
	public List<City> findAll() {
		return cityRepository.findAll();
	}

	@Override
	public Double calculateDistance(Long cityId1, Long cityId2) throws CityNotFoundException {
		return calculateDistance(cityId1, cityId2, Unit.MILES);
	}
	
	@Override
	public Double calculateDistance(Long cityId1, Long cityId2, Unit unit) throws CityNotFoundException {
		City origin = findOne(cityId1);
		City destination = findOne(cityId2);
		
		return Distance.calculate(origin.getLatitude(), 
				origin.getLongitude(), 
				destination.getLatitude(), 
				destination.getLongitude(), 
				unit);
	}
	
	@Override
	public Boolean exists(Long id) {
		return cityRepository.existsById(id);
	}
}
