package com.github.dmitributorchin.legal.helper.agency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgencyRepository extends JpaRepository<AgencyEntity, UUID> {
}
