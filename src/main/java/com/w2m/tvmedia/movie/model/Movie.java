package com.w2m.tvmedia.movie.model;

import java.util.Set;

import com.w2m.tvmedia.common.base.BaseEntity;
import com.w2m.tvmedia.spaceship.model.Spaceship;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Movie extends BaseEntity {

	@ManyToMany(mappedBy = "movies")
	Set<Spaceship> spaceships;

}
