package com.pigdroid.diet.service;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.util.Calendar;
import java.util.Date;
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
		if (null == journalEntry.getDate()) {
			journalEntry.setDate(new Date());
		}
		return journalRepository.save(journalEntry);
	}

	public List<JournalEntry> find(FindRequestDTO request) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(request.getByDate() != null ? request.getByDate() : new Date());
		calendar.set(HOUR_OF_DAY, 0);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.set(MILLISECOND, 0);
		Date fromDate = calendar.getTime();

		calendar.add(DAY_OF_MONTH, 1);
		Date toDate = calendar.getTime();
		Long userId = request.getUserId();
		List<JournalEntry> ret;
		if (userId != null) {
			ret = journalRepository.findByUserIdAndDateGreaterThanAndDateLessThan(userId, fromDate, toDate);
		} else {
			ret = journalRepository.findByDateGreaterThanAndDateLessThan(fromDate, toDate);
		}
		return ret;
	}

}
