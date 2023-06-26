package com.github.dmitributorchin.legal.helper.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
