package com.github.dmitributorchin.legal.helper.lawyer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LawyerRepository extends JpaRepository<LawyerEntity, UUID> {
}
