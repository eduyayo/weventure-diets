package com.pigdroid.diet.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pigdroid.diet.entity.JournalEntry;

public interface JournalRepository extends PagingAndSortingRepository<JournalEntry, Long> {

	List<JournalEntry> findByUserIdAndDateGreaterThanAndDateLessThan(Long userId, Date fromDate, Date toDate);

	List<JournalEntry> findByDateGreaterThanAndDateLessThan(Date fromDate, Date toDate);

}
