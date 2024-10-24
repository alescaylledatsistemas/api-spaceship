package com.w2m.tvmedia.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w2m.tvmedia.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
