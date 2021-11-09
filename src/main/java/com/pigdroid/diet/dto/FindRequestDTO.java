package com.pigdroid.diet.dto;

import java.util.Date;

import lombok.Data;

@Data
public class FindRequestDTO {

	Date byDate;
	Long userId;
}
