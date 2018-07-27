package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	City findByName(String name);
}
