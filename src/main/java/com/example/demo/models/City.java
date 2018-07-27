package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class City {

	@Id
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private Double latitude;
	
	@NotNull
	private Double longitude;

}
