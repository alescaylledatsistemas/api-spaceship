package com.w2m.tvmedia.spaceship.service.impl.utils;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.w2m.tvmedia.common.base.AbstractConverter;
import com.w2m.tvmedia.spaceship.model.Spaceship;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipListDTO;

@Component
public class SpaceshipListConverter extends AbstractConverter<Spaceship, SpaceshipListDTO> {

	public SpaceshipListConverter(DozerBeanMapper mapper) {
		super(mapper, Spaceship.class, SpaceshipListDTO.class);
	}

}
