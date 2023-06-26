package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import com.github.dmitributorchin.legal.helper.region.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements ApplicationRunner {
    private final RegionRepository regionRepository;

    @Override
    public void run(ApplicationArguments args) {
        regionRepository.saveAll(List.of(
                createRegion("Местный Суд"),
                createRegion("Верховный Суд")
        ));
    }

    private RegionEntity createRegion(String title) {
        var entity = new RegionEntity();
        entity.setTitle(title);
        return entity;
    }
}
