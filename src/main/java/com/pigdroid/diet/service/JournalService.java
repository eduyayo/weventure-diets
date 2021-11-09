package com.pigdroid.diet.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pigdroid.diet.dto.FindRequestDTO;
import com.pigdroid.diet.entity.JournalEntry;
import com.pigdroid.diet.entity.UnitType;
import com.pigdroid.diet.repository.JournalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JournalService {

	private final JournalRepository journalRepository;

	public void deleteById(Long id) {
		journalRepository.deleteById(id);
	}

	public JournalEntry create(JournalEntry journalEntry) {
		journalEntry.setId(null);
		if (null == journalEntry.getAmount()) {
			journalEntry.setAmount(1);
		}
		if (null == journalEntry.getUnitType()) {
			journalEntry.setUnitType(UnitType.UNIT);
		}
		return journalRepository.save(journalEntry);
	}

	public List<JournalEntry> find(FindRequestDTO request) {
		return Collections.emptyList();
	}

}
