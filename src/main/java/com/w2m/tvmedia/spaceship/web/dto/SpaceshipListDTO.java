package com.w2m.tvmedia.spaceship.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = { "id" })
public class SpaceshipListDTO {

	@NotNull
	private Long id;

	@NotBlank
	private String name;

	private Integer releaseYear;

	public SpaceshipListDTO() {
		super();
	}

	public SpaceshipListDTO(@NotNull Long id, @NotBlank String name, Integer releaseYear) {
		super();
		this.id = id;
		this.name = name;
		this.releaseYear = releaseYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
