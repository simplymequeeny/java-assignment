package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exceptions.CityNotFoundException;
import com.example.demo.models.City;
import com.example.demo.services.CityService;

@Controller
@RequestMapping("/api/cities")
public class CityController {

	@Autowired private CityService cityService;
	
	@GetMapping()
	@ResponseBody
	public List<City> findAll() {
		return cityService.findAll();
	}
	
	@PostMapping()
	public ResponseEntity<?> add(@RequestBody @Valid City city) {
		URI loc = getLocation(cityService.save(city));

		return ResponseEntity.created(loc).build();
	}
	
	@GetMapping(path = "/{id}")
	@ResponseBody
	public ResponseEntity<?> findOne(@PathVariable Long id) {
		City city = null;
		
		try {
			city = cityService.findOne(id);
		}
		catch (CityNotFoundException e) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok(city);
	}
	
	@GetMapping(path = "/calculate-distance/{originCityId}/{destinationCityId}")
	@ResponseBody
	public ResponseEntity<?> calculateDistance(@PathVariable Long originCityId, 
			@PathVariable Long destinationCityId) {
		
		Double distance = null;
		
		try {
			distance = cityService.calculateDistance(originCityId, destinationCityId);
		} 
		catch (CityNotFoundException e) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok(distance);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (!cityService.exists(id)) return ResponseEntity.badRequest().build();
		
		cityService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
		System.out.println("request body " + city);
		
		if (!cityService.exists(id)) return ResponseEntity.badRequest().build();

		cityService.save(city);
		
		return ResponseEntity.noContent().build();
	}
	
	private URI getLocation(City model) {
		return ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(model.getId()).toUri();
	}
}
