package com.w2m.tvmedia.spaceship.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;

import com.w2m.tvmedia.common.exception.W2MNotFoundException;
import com.w2m.tvmedia.spaceship.dao.SpaceshipRepository;
import com.w2m.tvmedia.spaceship.model.Spaceship;
import com.w2m.tvmedia.spaceship.service.impl.utils.SpaceshipConverter;
import com.w2m.tvmedia.spaceship.service.impl.utils.SpaceshipListConverter;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipDTO;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipListDTO;

class SpaceshipServiceTest {

	@InjectMocks
	private SpaceshipServiceImpl spaceshipService;

	@Mock
	private SpaceshipRepository spaceshipRepository;

	@Mock
	private SpaceshipConverter spaceshipConverter;

	@Mock
	private SpaceshipListConverter spaceshipListConverter;

	@Mock
	private CacheManager cacheManager;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll_Empty() {
		List<Spaceship> spaceships = new ArrayList<>();
		Spaceship spaceship1 = new Spaceship();
		spaceships.add(spaceship1);

		when(spaceshipRepository.findAll()).thenReturn(spaceships);
		when(spaceshipListConverter.convertEntityToDTO(spaceships)).thenReturn(new ArrayList<>());

		List<SpaceshipListDTO> result = spaceshipService.findAll();

		assertThat(result).isNotNull().isEmpty();
	}

	@Test
	void testFindAll_NotEmpty() {
		List<Spaceship> spaceships = new ArrayList<>();
		Spaceship spaceship = new Spaceship();
		spaceships.add(spaceship);

		List<SpaceshipListDTO> spaceshipDTOs = new ArrayList<>();
		SpaceshipListDTO spaceshipDTO = new SpaceshipListDTO();
		spaceshipDTOs.add(spaceshipDTO);

		when(spaceshipRepository.findAll()).thenReturn(spaceships);
		when(spaceshipListConverter.convertEntityToDTO(spaceships)).thenReturn(spaceshipDTOs);

		List<SpaceshipListDTO> result = spaceshipService.findAll();

		assertThat(result).isNotNull().isNotEmpty();
	}

	@Test
	void testFindById_SpaceshipExists() {
		Long id = 1L;
		Spaceship spaceship = new Spaceship();
		SpaceshipListDTO dto = new SpaceshipListDTO();

		when(spaceshipRepository.findById(id)).thenReturn(Optional.of(spaceship));
		when(spaceshipListConverter.convertEntityToDTO(spaceship)).thenReturn(dto);

		SpaceshipListDTO result = spaceshipService.findById(id);

		assertThat(dto.getId()).isEqualTo(result.getId());
	}

	@Test()
	void testFindById_SpaceshipNotFound() {
		Long id = 1L;

		when(spaceshipRepository.findById(id)).thenReturn(Optional.empty());

		assertThatExceptionOfType(W2MNotFoundException.class).isThrownBy(() -> {
			spaceshipService.findById(id);
		}).withMessage("Entity SPACESHIP with id 1 not found");
	}

	@Test
	void testCreate() {
		SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
		Spaceship spaceship = new Spaceship();
		SpaceshipListDTO dto = new SpaceshipListDTO();

		when(spaceshipConverter.convertDTOToEntity(spaceshipDTO)).thenReturn(spaceship);
		when(spaceshipRepository.save(spaceship)).thenReturn(spaceship);
		when(spaceshipListConverter.convertEntityToDTO(spaceship)).thenReturn(dto);

		SpaceshipListDTO result = spaceshipService.create(spaceshipDTO);

		assertThat(dto).isEqualTo(result);
		verify(cacheManager).getCacheNames();
	}

	@Test
	void testUpdateById_SpaceshipExists() {
		Long id = 1L;
		Spaceship existingSpaceship = new Spaceship();
		SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
		Spaceship updatedSpaceship = new Spaceship();
		SpaceshipListDTO dto = new SpaceshipListDTO();

		when(spaceshipRepository.findById(id)).thenReturn(Optional.of(existingSpaceship));
		when(spaceshipConverter.updateEntityFromDTO(existingSpaceship, spaceshipDTO)).thenReturn(updatedSpaceship);
		when(spaceshipRepository.save(updatedSpaceship)).thenReturn(updatedSpaceship);
		when(spaceshipListConverter.convertEntityToDTO(updatedSpaceship)).thenReturn(dto);

		SpaceshipListDTO result = spaceshipService.updateById(id, spaceshipDTO);

		assertThat(dto).isEqualTo(result);
		verify(spaceshipRepository).findById(id);
		verify(spaceshipRepository).save(updatedSpaceship);
	}

	@Test
	void testUpdateById_SpaceshipNotFound() {
		Long id = 1L;
		SpaceshipDTO spaceshipDTO = new SpaceshipDTO();

		when(spaceshipRepository.findById(id)).thenReturn(Optional.empty());

		assertThatExceptionOfType(W2MNotFoundException.class).isThrownBy(() -> {
			spaceshipService.updateById(id, spaceshipDTO);
		}).withMessage("Entity SPACESHIP with id 1 not found");
	}

	@Test
	void testDeleteById_SpaceshipExists() {
		Long id = 1L;

		when(spaceshipRepository.findById(id)).thenReturn(Optional.of(new Spaceship()));

		spaceshipService.deleteById(id);

		verify(spaceshipRepository).deleteById(id);
	}

	@Test
	void testDeleteById_SpaceshipNotFound() {
		Long id = 1L;

		when(spaceshipRepository.findById(id)).thenReturn(Optional.empty());

		assertThatExceptionOfType(W2MNotFoundException.class).isThrownBy(() -> {
			spaceshipService.deleteById(id);
		}).withMessage("Entity SPACESHIP with id 1 not found");
	}

}
