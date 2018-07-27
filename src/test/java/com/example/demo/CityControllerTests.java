package com.example.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.example.demo.models.City;
import com.example.demo.repositories.CityRepository;
import com.example.demo.utils.Distance;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTests {
	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	@Autowired MockMvc mockMvc;
	@Autowired CityRepository cityRepository;
	
	@Test
	public void contextLoads() {
		Assert.notNull(mockMvc, "mockMVC is null");
	}

	@Test
	public void shouldReturnBadRequestWhenCityIdDoesNotExists() throws Exception {
		mockMvc.perform(get("/api/cities/111"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldGetChicagoDetails() throws Exception {
		mockMvc.perform(get("/api/cities/6"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(6)))
				.andExpect(jsonPath("$.name", is("Chicago")))
				.andExpect(jsonPath("$.latitude", is(41.85)))
				.andExpect(jsonPath("$.longitude", is(-87.65)));
	}
	
	@Ignore
	public void shouldRetrieveAllCities() throws Exception {
		mockMvc.perform(get("/api/cities"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(15)));
	}
	
	@Test
	public void shouldReturnBadRequestStatusWhenCalculatingDistanceAndCityIdDoesNotExists() throws Exception {
		mockMvc.perform(get("/api/cities/calculate-distance/111/2"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldCalculateTheDistanceBetweenTokyoAndLondonInMiles() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/cities/calculate-distance/1/2"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		Assert.notNull(content, "content is null");
		Assert.isTrue(Distance.round(Double.valueOf(content).doubleValue(), 2) == 5939.69, 
				"distance is not correct");
	}
	
	@Test
	public void shouldAddWaterlooToTheListOfCities() throws Exception {
		City waterloo = new City(16l, "Waterloo", 43.466667, -80.516667);
		String json = json(waterloo);
		
		mockMvc.perform(post("/api/cities")
					.contentType(contentType)
					.content(json))
				.andExpect(status().isCreated());
		
		Assert.notNull(cityRepository.findByName(waterloo.getName()), "waterloo city was not added");
	}
	
	@Test 
	public void shouldReturnBadRequestWhenDeletingANonExistentId() throws Exception {
		mockMvc.perform(delete("/api/cities/222"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldDeleteBangkokCity() throws Exception {
		mockMvc.perform(delete("/api/cities/15"))
				.andExpect(status().isNoContent());
		
		Assert.isTrue(!cityRepository.existsById(15l), "Miami city was not deleted");
	}
	
	@Test 
	public void shouldReturnBadRequestWhenUpdatingANonExistentId() throws Exception {
		mockMvc.perform(put("/api/cities/222"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldPersistModifiedValuesForMilan() throws Exception {
		City city = cityRepository.findById(8l).get();
		
		city.setName("Temp City");
		city.setLatitude(-22.222);
		city.setLongitude(22.222);
		
		String json = json(city);
		
		mockMvc.perform(put("/api/cities/8")
					.contentType(contentType)
					.content(json))
				.andExpect(status().isNoContent());
		
		Assert.isTrue(cityRepository.findById(city.getId())
				.get().equals(city), "modified city did not persists");
	}
	    
	private String json(Object o) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(o);
	}
}
