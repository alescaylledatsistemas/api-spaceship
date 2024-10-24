package com.w2m.tvmedia.spaceship.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.w2m.tvmedia.spaceship.model.Spaceship;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

	@Query(value = "SELECT s FROM Spaceship s ORDER BY id")
	List<Spaceship> findAllSpaceshipsWithPagination(Pageable pageable);

	@Query("SELECT s FROM Spaceship s WHERE s.name LIKE %?1%")
	List<Spaceship> findByNameContainingText(String text);

}
