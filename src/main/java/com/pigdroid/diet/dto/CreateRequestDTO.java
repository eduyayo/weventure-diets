package com.pigdroid.diet.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pigdroid.diet.entity.UnitType;

import lombok.Data;

@Data
public class CreateRequestDTO {

	private Long id;

    @NotBlank(message = "food name is mandatory")
	private String name;
	private Integer amount;
	private UnitType unitType;

    @NotNull(message = "user id is mandatory")
	private Long userId;

	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;

}
