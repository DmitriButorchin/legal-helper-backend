package com.github.dmitributorchin.legal.helper.region;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    public List<Region> getAllRegions() {
        return regionRepository.findAll()
                .stream()
                .map(entity -> new Region(entity.getId().toString(), entity.getTitle()))
                .collect(Collectors.toList());
    }
}
