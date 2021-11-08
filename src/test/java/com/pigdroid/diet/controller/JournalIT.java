package com.pigdroid.diet.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigdroid.diet.entity.JournalEntry;
import com.pigdroid.diet.entity.UnitType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JournalIT {

	private static final String BASE_URL = "http://localhost:%d/";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private ObjectMapper mapper = new ObjectMapper();

	private JournalEntry assertCreated(Date date) throws JsonProcessingException {
		return assertCreated(1L, date);
	}

	private JournalEntry assertCreated(long userId, Date date) throws JsonProcessingException {
		return assertCreated(userId, "donut", date);
	}

	private JournalEntry assertCreated(long userId, String foodName, Date date) throws JsonProcessingException {
		JournalEntry food = new JournalEntry();
		food.setName(foodName);
		food.setUnitType(UnitType.UNIT);
		food.setUserId(userId);

	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
	    headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
	    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<String> requestEntity =
	    	      new HttpEntity<String>(mapper.writeValueAsString(food), headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(getLocalhost() + "/journal", requestEntity, String.class);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

		return mapper.readValue(responseEntity.getBody(), JournalEntry.class);
	}

	private String getLocalhost() {
		return String.format(BASE_URL, port);
	}

	private JournalEntry assertCreateAndRetrieve() throws JsonMappingException, JsonProcessingException {
		JournalEntry created = assertCreated(new Date());
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(getLocalhost() + "/journal", String.class);
		List<JournalEntry> list = mapper.readValue(responseEntity.getBody(), new TypeReference<List<JournalEntry>>() {
		});
		JournalEntry found = findEntry(created, list);
		assertThat(found).isNotNull();
		assertThat(found).usingRecursiveComparison().isEqualTo(created);
		return created;
	}

	@Test
	@DisplayName("when providing correct data then the controller responds with 201 Created and can retrieve the created records")
	void testCanCreateAndGetTheNewRecords() throws Exception {
		assertCreateAndRetrieve();
	}

	@Test
	@DisplayName("when requested to delete one record by id then the controller responds with a list of records without the deleted one")
	void testCanDeleteAndRetrieveChanges() throws Exception {
		JournalEntry created = assertCreateAndRetrieve();
		restTemplate.delete(getLocalhost() + "/journal/" + created.getId());

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(getLocalhost() + "/journal", String.class);
		List<JournalEntry> list = mapper.readValue(responseEntity.getBody(), new TypeReference<List<JournalEntry>>() {
		});
		JournalEntry found = findEntry(created, list);
		assertThat(found).isNull();
	}

	private JournalEntry findEntry(JournalEntry created, List<JournalEntry> list) {
		JournalEntry found = null;
		Iterator<JournalEntry> iterator = list.iterator();
		while (found == null && iterator.hasNext()) {
			JournalEntry current = iterator.next();
			if (current.getId().equals(created.getId())) {
				found = current;
			}
		}
		return found;
	}

	@Test
	@DisplayName("when listing by date then application responds with different data for different days")
	void testCanListWithFilter() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		JournalEntry yesterMeal = assertCreated(calendar.getTime());
		JournalEntry hotMeal = assertCreated(new Date());

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(getLocalhost() + "/journal?byDate=" + toString(new Date()), String.class);
		List<JournalEntry> list = mapper.readValue(responseEntity.getBody(), new TypeReference<List<JournalEntry>>() {
		});
		assertThat(findEntry(yesterMeal, list)).isNull();
		assertThat(findEntry(hotMeal, list)).isNotNull();

		responseEntity = restTemplate.getForEntity(getLocalhost() + "/journal?byDate=" + toString(calendar.getTime()), String.class);
		list = mapper.readValue(responseEntity.getBody(), new TypeReference<List<JournalEntry>>() {
		});
		assertThat(findEntry(yesterMeal, list)).isNotNull();
		assertThat(findEntry(hotMeal, list)).isNull();

	}

	private String toString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(date);
	}

	@Test
	@DisplayName("when creating with wrong data then the application responds with 400 Bad Request")
	void testCannotCreateWithMissingMandatoryFoodName() {
		throw new AssertionError("Not implemented");
	}

	@Test
	@DisplayName("when listing without a date parameter then the application responds with the list for today")
	void testListWithDefaults() {
		throw new AssertionError("Not implemented");
	}

	@Test
	@DisplayName("when creating without unit and types then the application creates the register with 1 unit")
	void testCreatedWithDefaults() {
		throw new AssertionError("Not implemented");
	}

}
