package com.w2m.tvmedia.serie.model;

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
public class Serie extends BaseEntity {

	@ManyToMany(mappedBy = "series")
	Set<Spaceship> spaceships;

}
