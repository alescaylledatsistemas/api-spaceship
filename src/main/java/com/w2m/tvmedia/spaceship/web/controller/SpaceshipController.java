package com.w2m.tvmedia.spaceship.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.w2m.tvmedia.client.kafka.producer.MessageProducer;
import com.w2m.tvmedia.spaceship.service.SpaceshipService;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipDTO;
import com.w2m.tvmedia.spaceship.web.dto.SpaceshipListDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/spaceships")
@Tag(name = "Spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;
	private final MessageProducer messageProducer;

	public SpaceshipController(SpaceshipService spaceshipService, MessageProducer messageProducer) {
		super();
		this.spaceshipService = spaceshipService;
		this.messageProducer = messageProducer;
	}

	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping()
	public ResponseEntity<List<SpaceshipListDTO>> findAll(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		List<SpaceshipListDTO> spaceshipDTOs;
		if (page != null && limit != null) {
			spaceshipDTOs = spaceshipService.findAllSpaceshipsWithPagination(page, limit);
		} else {
			spaceshipDTOs = spaceshipService.findAll();
		}

		if (spaceshipDTOs != null && !spaceshipDTOs.isEmpty()) {
			return ResponseEntity.ok(spaceshipDTOs);
		}

		return ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping(path = "/{id}")
	public ResponseEntity<SpaceshipListDTO> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(spaceshipService.findById(id));
	}

	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping(path = "/name-contain")
	public ResponseEntity<List<SpaceshipListDTO>> findByNameContainingText(@RequestParam(value = "text") String text) {
		List<SpaceshipListDTO> spaceshipDTOs = null;
		if (text != null) {
			spaceshipDTOs = spaceshipService.findByNameContainingText(text);
		}

		if (spaceshipDTOs != null && !spaceshipDTOs.isEmpty()) {
			return ResponseEntity.ok(spaceshipDTOs);
		}

		return ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping()
	public ResponseEntity<SpaceshipListDTO> createSpaceship(@Validated @RequestBody SpaceshipDTO spaceshipDTO) {
		List<SpaceshipListDTO> spaceshipDTOs = spaceshipService.findByNameContainingText(spaceshipDTO.getName());
		if (spaceshipDTOs != null && !spaceshipDTOs.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		SpaceshipListDTO createdSpaceship = spaceshipService.create(spaceshipDTO);
		return ResponseEntity.created(URI.create("/api/v1/spaceships/" + createdSpaceship.getId()))
				.body(createdSpaceship);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(path = "/{id}")
	public ResponseEntity<SpaceshipListDTO> updateSpaceship(@PathVariable("id") Long id,
			@Validated @RequestBody SpaceshipDTO spaceshipDTO) {
		return ResponseEntity.ok(spaceshipService.updateById(id, spaceshipDTO));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		spaceshipService.deleteById(id);

		messageProducer.sendMessage("spaceship-topic", "The spaceship with id: " + id + " was deleted!");

		return ResponseEntity.noContent().build();
	}

}
