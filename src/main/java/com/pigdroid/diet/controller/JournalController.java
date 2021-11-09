package com.pigdroid.diet.controller;

import static com.pigdroid.diet.dto.JournalEntryMapper.MAPPER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pigdroid.diet.dto.CreateRequestDTO;
import com.pigdroid.diet.dto.FindRequestDTO;
import com.pigdroid.diet.entity.JournalEntry;
import com.pigdroid.diet.service.JournalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {

	private final JournalService journalService;

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<JournalEntry>> find(FindRequestDTO request) {
		List<JournalEntry> found = journalService.find(request);
		return ResponseEntity.ok(found);
	}

	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<JournalEntry> create(@RequestBody CreateRequestDTO request) {
		JournalEntry created = journalService.create(MAPPER.toJournalEntry(request));
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		journalService.deleteById(id);
		return ResponseEntity.ok().body(null);
	}

}
