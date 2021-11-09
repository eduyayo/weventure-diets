package com.pigdroid.diet.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FindRequestDTO {

	@DateTimeFormat(pattern="dd.MM.yyyy")
	Date byDate;
	Long userId;
}
