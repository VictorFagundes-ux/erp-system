package com.erp.erp_system.client.repository;

import com.erp.erp_system.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByDocument(String document);
    Optional<Client> findByEmail(String email);
}
