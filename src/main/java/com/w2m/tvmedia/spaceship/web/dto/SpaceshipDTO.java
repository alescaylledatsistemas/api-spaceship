package com.w2m.tvmedia.spaceship.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = { "name" })
public class SpaceshipDTO {

	@NotBlank
	private String name;

	private Integer releaseYear;

	public SpaceshipDTO() {
		super();
	}

	public SpaceshipDTO(@NotBlank String name, Integer releaseYear) {
		super();
		this.name = name;
		this.releaseYear = releaseYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

}
