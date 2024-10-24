package com.w2m.tvmedia.spaceship.service.impl.utils;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.w2m.tvmedia.common.base.AbstractConverter;
import com.w2m.tvmedia.spaceship.model.Spaceship;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipDTO;

@Component
public class SpaceshipConverter extends AbstractConverter<Spaceship, SpaceshipDTO> {

	public SpaceshipConverter(DozerBeanMapper mapper) {
		super(mapper, Spaceship.class, SpaceshipDTO.class);
	}

}
