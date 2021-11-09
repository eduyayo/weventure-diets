package com.pigdroid.diet.dto;

import com.pigdroid.diet.entity.UnitType;

import lombok.Data;

@Data
public class CreateRequestDTO {

	private Long id;
	private String name;
	private Integer amount;
	private UnitType unitType;
	private Long userId;

}
