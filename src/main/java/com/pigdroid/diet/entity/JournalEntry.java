package com.pigdroid.diet.entity;

import lombok.Data;

@Data
public class JournalEntry {

	private Long id;
	private String name;
	private Integer amount;
	private UnitType unitType;
	private Long userId;

}
