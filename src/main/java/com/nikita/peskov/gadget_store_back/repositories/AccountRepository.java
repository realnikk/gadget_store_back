package com.nikita.peskov.gadget_store_back.repositories;

import com.nikita.peskov.gadget_store_back.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Optional<Account> findByEmail(String email);
}