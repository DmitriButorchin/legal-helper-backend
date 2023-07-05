package com.github.dmitributorchin.legal.helper.correspondent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CorrespondentRepository extends JpaRepository<CorrespondentEntity, UUID> {
}
