package com.github.dmitributorchin.legal.helper.region;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionRepository extends JpaRepository<RegionEntity, UUID> {
}
