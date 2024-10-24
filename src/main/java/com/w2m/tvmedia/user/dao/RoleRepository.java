package com.w2m.tvmedia.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w2m.tvmedia.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
