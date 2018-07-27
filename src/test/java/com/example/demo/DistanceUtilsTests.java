package com.example.demo;

import org.junit.Test;
import org.springframework.util.Assert;

import com.example.demo.utils.Distance;
import com.example.demo.utils.Distance.Unit;

public class DistanceUtilsTests {
	@Test
	public void distanceCalculatedMustBeInMiles()  {
		double distance = Distance.calculate(35.685, 139.751389, 51.514125,-0.093689, Unit.MILES);
		Assert.isTrue(Distance.round(distance, 2)  == 5939.69, "distance is not correct");
	}
	
	@Test
	public void distanceCalculatedMustBeInKilometers() {
		double distance = Distance.calculate(35.685, 139.751389, 51.514125,-0.093689, Unit.KILOMETERS);
		Assert.isTrue(Distance.round(distance, 2)  == 9559.01, "distance is not correct");
	}
	
	@Test
	public void distanceCalculatedMustBeInNauticalMiles() {
		double distance = Distance.calculate(35.685, 139.751389, 51.514125,-0.093689, Unit.NAUTICAL_MILES);
		Assert.isTrue(Distance.round(distance, 2) == 5158.03, "distance is not correct");
	}
	
	@Test
	public void shouldRoundValueInto2DecimalPlaces() {
		Assert.isTrue(Distance.round(139.751389, 2) == 139.75, "round value not correct");
	}
}
