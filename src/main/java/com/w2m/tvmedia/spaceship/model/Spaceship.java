package com.w2m.tvmedia.spaceship.model;

import java.util.Set;

import com.w2m.tvmedia.common.base.BaseEntity;
import com.w2m.tvmedia.movie.model.Movie;
import com.w2m.tvmedia.serie.model.Serie;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
public class Spaceship extends BaseEntity {

	@ManyToMany
	@JoinTable(name = "serie_spaceship", joinColumns = @JoinColumn(name = "spaceship_id"), inverseJoinColumns = @JoinColumn(name = "serie_id"))
	private Set<Serie> series;

	@ManyToMany
	@JoinTable(name = "movie_spaceship", joinColumns = @JoinColumn(name = "spaceship_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> movies;

}
