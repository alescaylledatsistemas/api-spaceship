package com.w2m.tvmedia.spaceship.service;

import java.util.List;

import com.w2m.tvmedia.spaceship.web.dto.SpaceshipDTO;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipListDTO;

public interface SpaceshipService {

	List<SpaceshipListDTO> findAll();

	SpaceshipListDTO findById(Long id);

	SpaceshipListDTO create(SpaceshipDTO spaceship);

	SpaceshipListDTO updateById(Long id, SpaceshipDTO spaceship);

	void deleteById(Long id);

	List<SpaceshipListDTO> findByNameContainingText(String text);

	List<SpaceshipListDTO> findAllSpaceshipsWithPagination(int page, int limit);

}
