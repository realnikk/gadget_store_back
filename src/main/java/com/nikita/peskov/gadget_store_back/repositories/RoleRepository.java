package com.nikita.peskov.gadget_store_back.repositories;

import com.nikita.peskov.gadget_store_back.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String role);

    boolean existsByName(String role);
}
