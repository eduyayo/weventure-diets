package com.pigdroid.diet.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pigdroid.diet.entity.JournalEntry;

public interface JournalRepository extends PagingAndSortingRepository<JournalEntry, Long>{

}
