package com.w2m.tvmedia.spaceship.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.w2m.tvmedia.common.base.W2MEntity;
import com.w2m.tvmedia.common.exception.W2MNotFoundException;
import com.w2m.tvmedia.common.logging.W2MLog;
import com.w2m.tvmedia.spaceship.dao.SpaceshipRepository;
import com.w2m.tvmedia.spaceship.model.Spaceship;
import com.w2m.tvmedia.spaceship.service.SpaceshipService;
import com.w2m.tvmedia.spaceship.service.impl.utils.SpaceshipConverter;
import com.w2m.tvmedia.spaceship.service.impl.utils.SpaceshipListConverter;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipDTO;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipListDTO;

@Service
@Transactional
public class SpaceshipServiceImpl implements SpaceshipService {

	private final SpaceshipRepository spaceshipRepository;
	private final SpaceshipConverter spaceshipConverter;
	private final SpaceshipListConverter spaceshipListConverter;
	private final CacheManager cacheManager;

	public SpaceshipServiceImpl(SpaceshipRepository spaceshipRepository, SpaceshipConverter spaceshipConverter,
			SpaceshipListConverter spaceshipListConverter, CacheManager cacheManager) {
		this.spaceshipRepository = spaceshipRepository;
		this.spaceshipConverter = spaceshipConverter;
		this.spaceshipListConverter = spaceshipListConverter;
		this.cacheManager = cacheManager;
	}

	private SpaceshipListDTO save(Spaceship spaceship) {
		return spaceshipListConverter.convertEntityToDTO(spaceshipRepository.save(spaceship));
	}

	@Override
	@Cacheable("spaceships")
	public List<SpaceshipListDTO> findAll() {
		return spaceshipListConverter.convertEntityToDTO(spaceshipRepository.findAll());
	}

	@W2MLog
	@Override
	public SpaceshipListDTO findById(Long id) {
		Optional<Spaceship> optional = spaceshipRepository.findById(id);
		if (optional.isEmpty())
			throw new W2MNotFoundException(W2MEntity.SPACESHIP, id);
		return spaceshipListConverter.convertEntityToDTO(optional.get());
	}

	@Override
	public SpaceshipListDTO create(SpaceshipDTO spaceship) {
		evictAllCaches();
		return save(spaceshipConverter.convertDTOToEntity(spaceship));
	}

	@W2MLog
	@Override
	public SpaceshipListDTO updateById(Long id, SpaceshipDTO spaceship) {
		evictAllCaches();
		return spaceshipRepository.findById(id)
				.map(entity -> save(spaceshipConverter.updateEntityFromDTO(entity, spaceship)))
				.orElseThrow(() -> new W2MNotFoundException(W2MEntity.SPACESHIP, id));
	}

	@W2MLog
	@Override
	public void deleteById(Long id) {
		evictAllCaches();
		if (spaceshipRepository.findById(id).isPresent()) {
			spaceshipRepository.deleteById(id);
		} else {
			throw new W2MNotFoundException(W2MEntity.SPACESHIP, id);
		}
	}

	@Override
	public List<SpaceshipListDTO> findByNameContainingText(String text) {
		return spaceshipListConverter.convertEntityToDTO(spaceshipRepository.findByNameContainingText(text));
	}

	@Override
	public List<SpaceshipListDTO> findAllSpaceshipsWithPagination(int page, int limit) {
		return spaceshipListConverter.convertEntityToDTO(
				spaceshipRepository.findAllSpaceshipsWithPagination(Pageable.ofSize(limit).withPage(page)));
	}

	private void evictAllCaches() {
		cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

}
