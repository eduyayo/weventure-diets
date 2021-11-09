package com.pigdroid.diet.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pigdroid.diet.entity.JournalEntry;

@Mapper
public interface JournalEntryMapper {

	JournalEntryMapper MAPPER = Mappers.getMapper( JournalEntryMapper.class );

    JournalEntry toJournalEntry(CreateRequestDTO car);

}
