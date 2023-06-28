package com.github.dmitributorchin.legal.helper.claim;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClaimRepository extends JpaRepository<ClaimEntity, UUID> {
}
