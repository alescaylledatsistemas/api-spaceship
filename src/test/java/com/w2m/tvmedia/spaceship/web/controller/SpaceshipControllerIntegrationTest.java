package com.w2m.tvmedia.spaceship.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.w2m.tvmedia.common.security.JwtTokenProvider;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipDTO;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipListDTO;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SpaceshipControllerIntegrationTest {

	private static final String Location = "Location";
	private static final Long NON_EXISTING_MOVE_ID = 999L;
	private static final String[] SAMPLE_SPACESHIPS_NAMES = { "Spaceship 1", "Spaceship 2", "Spaceship 12",
			"Spaceship 8", "Spaceship 5" };
	private static final String[] SAMPLE_SPACESHIPS_NAMES_CONTAIN_1 = { "Spaceship 1", "Spaceship 10", "Spaceship 12",
			"Spaceship 15" };

	private static final String SPACESHIP_30 = "Spaceship 30";
	private static final String SPACESHIP_20 = "Spaceship 20";
	private static final String SPACESHIP_2 = "Spaceship 2";
	private static final String SPACESHIP_4 = "Spaceship 4";

	static final long EXPIRATIONTIME = 1800000; // 10 days
	private static final String ADMIN = "admin";
	private static final String USER = "user";

	@LocalServerPort
	private int localPort;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@BeforeEach
	public void setUp() {
		SecurityContextHolder.clearContext();
	}

	private HttpHeaders getHeaders(String username) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, username));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token;
		if (username.equals(USER)) {
			token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(USER, USER,
					Collections.singletonList(new SimpleGrantedAuthority(USER.toUpperCase()))));
		} else {
			token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(ADMIN, ADMIN,
					AuthorityUtils.createAuthorityList(USER.toUpperCase(), ADMIN.toUpperCase())));
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		return headers;
	}

	private String getSpaceshipsApiUrl() {
		return getServerUrl("/api/v1/spaceships");
	}

	private String getServerUrl(String endpoint) {
		return String.format("http://localhost:%d%s", localPort, endpoint);
	}

	private String getSpaceshipIdUrl(long id) {
		return getSpaceshipsApiUrl() + "/" + id;
	}

	private String getSpaceshipNameContainUrl(String text) {
		return getSpaceshipsApiUrl() + "/name-contain?text=" + text;
	}

	@Test
	void shouldGetSpaceships() {
		// when
		ResponseEntity<List<SpaceshipListDTO>> response = getAllspaceships();

		// then
		assertSamplespaceshipsRetrieved(response, SAMPLE_SPACESHIPS_NAMES);
	}

	private ResponseEntity<List<SpaceshipListDTO>> getAllspaceships() {
		HttpEntity<SpaceshipDTO> request = new HttpEntity<>(getHeaders(USER));
		return restTemplate.exchange(getSpaceshipsApiUrl(), HttpMethod.GET, request,
				new ParameterizedTypeReference<>() {
				});
	}

	private void assertSamplespaceshipsRetrieved(ResponseEntity<List<SpaceshipListDTO>> response, String[] names) {
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull().isNotEmpty();
		assertThat(response.getBody()).isNotNull().isNotEmpty().extracting(SpaceshipListDTO::getName)
				.containsAnyOf(names);
	}

	@Test
	void shouldGetSpaceshipById() {
		// given
		Long spaceshipIdToRetrieve = 4L;

		// when
		ResponseEntity<SpaceshipListDTO> response = getSpaceshipById(spaceshipIdToRetrieve, USER);

		// then
		assertSpaceshipRetrieved(response, SPACESHIP_4);
	}

	@Test
	void shouldGetSpaceshipsByNameContainText() {
		// given
		String text = "1";

		// when
		HttpEntity<String> request = new HttpEntity<>("", getHeaders(USER));
		ResponseEntity<List<SpaceshipListDTO>> response = restTemplate.exchange(getSpaceshipNameContainUrl(text),
				HttpMethod.GET, request, new ParameterizedTypeReference<>() {
				});

		// then
		assertSamplespaceshipsRetrieved(response, SAMPLE_SPACESHIPS_NAMES_CONTAIN_1);
	}

	private ResponseEntity<SpaceshipListDTO> getSpaceshipById(Long spaceshipIdToRetrieve, String user) {
		HttpEntity<String> request = new HttpEntity<>("", getHeaders(user));
		return restTemplate.exchange(getSpaceshipIdUrl(spaceshipIdToRetrieve), HttpMethod.GET, request,
				SpaceshipListDTO.class);
	}

	private void assertSpaceshipRetrieved(ResponseEntity<SpaceshipListDTO> response, String name) {
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull().extracting(SpaceshipListDTO::getName).isEqualTo(name);
	}

	@Test
	void shouldReportUnauthorized() {
		// when
		HttpEntity<String> request = new HttpEntity<>("");
		ResponseEntity<SpaceshipListDTO> response = restTemplate.exchange(getSpaceshipIdUrl(5L), HttpMethod.GET,
				request, SpaceshipListDTO.class);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		assertThat(response.getBody()).isNull();
	}

	@Test
	void shouldReportNonExistingSpaceship() {
		// when
		HttpEntity<String> request = new HttpEntity<>("", getHeaders(USER));
		ResponseEntity<String> response = restTemplate.exchange(getSpaceshipIdUrl(NON_EXISTING_MOVE_ID), HttpMethod.GET,
				request, String.class);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNotBlank()
				.isEqualTo("Entity SPACESHIP with id " + NON_EXISTING_MOVE_ID + " not found");
	}

	@Test
	void shouldCreateSpaceship() {
		// given
		var spaceshipToCreate = new SpaceshipDTO();
		spaceshipToCreate.setName(SPACESHIP_30);
		spaceshipToCreate.setReleaseYear(2024);

		// when
		ResponseEntity<SpaceshipListDTO> response = createSpaceship(spaceshipToCreate);

		// then
		assertSpaceshipCreated(response, spaceshipToCreate);
	}

	private ResponseEntity<SpaceshipListDTO> createSpaceship(SpaceshipDTO spaceship) {
		HttpEntity<SpaceshipDTO> request = new HttpEntity<>(spaceship, getHeaders(ADMIN));
		return restTemplate.exchange(getSpaceshipsApiUrl(), HttpMethod.POST, request, SpaceshipListDTO.class);
	}

	private void assertSpaceshipCreated(ResponseEntity<SpaceshipListDTO> response, SpaceshipDTO spaceshipToCreate) {
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders()).containsEntry(Location, Collections.singletonList("/api/v1/spaceships/16"));
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull().isNotZero();
		assertThat(response.getBody().getName()).isEqualTo(spaceshipToCreate.getName());
		assertThat(response.getBody().getReleaseYear()).isEqualTo(spaceshipToCreate.getReleaseYear());
	}

	@Test
	void shouldUpdateSpaceship() {
		// given
		var spaceshipIdToUpdate = 2L;
		var spaceshipDataToUpdate = new SpaceshipDTO();
		spaceshipDataToUpdate.setName(SPACESHIP_20);
		spaceshipDataToUpdate.setReleaseYear(1968);

		var expectedspaceshipDataAfterUpdate = new SpaceshipListDTO();
		expectedspaceshipDataAfterUpdate.setId(spaceshipIdToUpdate);
		expectedspaceshipDataAfterUpdate.setName(spaceshipDataToUpdate.getName());
		expectedspaceshipDataAfterUpdate.setReleaseYear(spaceshipDataToUpdate.getReleaseYear());

		// when
		ResponseEntity<SpaceshipListDTO> responseBeforeUpdate = getSpaceshipById(spaceshipIdToUpdate, ADMIN);
		ResponseEntity<SpaceshipListDTO> updateResponse = updateSpaceshipById(spaceshipIdToUpdate,
				spaceshipDataToUpdate);
		ResponseEntity<SpaceshipListDTO> responseAfterUpdate = getSpaceshipById(spaceshipIdToUpdate, ADMIN);

		// then
		assertSpaceshipRetrieved(responseBeforeUpdate, SPACESHIP_2);
		assertSpaceshipRetrieved(updateResponse, expectedspaceshipDataAfterUpdate.getName());
		assertSpaceshipRetrieved(responseAfterUpdate, expectedspaceshipDataAfterUpdate.getName());
	}

	private ResponseEntity<SpaceshipListDTO> updateSpaceshipById(long spaceshipIdToUpdate,
			SpaceshipDTO spaceshipDataToUpdate) {
		HttpEntity<SpaceshipDTO> request = new HttpEntity<>(spaceshipDataToUpdate, getHeaders(ADMIN));
		return restTemplate.exchange(getSpaceshipIdUrl(spaceshipIdToUpdate), HttpMethod.PUT, request,
				new ParameterizedTypeReference<>() {
				});
	}

	@Test
	void shouldDeleteSpaceship() {
		// given
		var spaceshipToDelete = 5L;

		// when
		ResponseEntity<Void> response = deleteSpaceshipById(spaceshipToDelete);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	private ResponseEntity<Void> deleteSpaceshipById(Long spaceshipToDelete) {
		HttpEntity<SpaceshipDTO> request = new HttpEntity<>(getHeaders(ADMIN));
		return restTemplate.exchange(getSpaceshipIdUrl(spaceshipToDelete), HttpMethod.DELETE, request, Void.class);
	}

}