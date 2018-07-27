package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.example.demo.exceptions.CityNotFoundException;
import com.example.demo.models.City;
import com.example.demo.services.CityService;
import com.example.demo.utils.Distance;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityServiceTests {
	
	@Autowired CityService service;
	
	@Before
	public void setup() {
		Assert.notNull(service, "service is null");
	}
	
	@Test
	public void shouldGet15Records() {
		int count = service.findAll().size();
		Assert.isTrue(count == 15, "records count is not 15");
	}
	
	@Test(expected = CityNotFoundException.class)
	public void shouldThrowCityNotFoundExceptionWhenCityIdDoesNotExists() throws CityNotFoundException {
		service.findOne(333);
	}
	
	@Test
	public void shouldGetParis() throws CityNotFoundException {
		City paris = service.findOne(9);
		
		Assert.notNull(paris, "object is null");
		Assert.isTrue(paris.getId().equals(9), "Paris ID is not 9");
		Assert.isTrue(paris.getName().equals("Paris"), "Name is not Paris");
		Assert.isTrue(paris.getLatitude().equals(48.866667), "Paris latitude is wrong");
		Assert.isTrue(paris.getLongitude().equals(2.333333), "Paris longitude is wrong");
	}
	
	@Test(expected = CityNotFoundException.class)
	public void shouldThrowCityNotFoundExceptionWhen1stParameterIdDoesNotExists() throws CityNotFoundException {
		service.calculateDistance(111, 1);
	}

	@Test(expected = CityNotFoundException.class)
	public void shouldThrowCityNotFoundExceptionWhen2ndParameterIdDoesNotExists() throws CityNotFoundException {
		service.calculateDistance(1, 222);
	}
	
	@Test
	public void shouldCalculateDistanceBetweenTokyoAndLondonInMiles() throws CityNotFoundException {
		Double distance = service.calculateDistance(1, 2);
		Assert.isTrue(Distance.round(distance, 2) == 5939.69, "distance is not correct");
	}
}
