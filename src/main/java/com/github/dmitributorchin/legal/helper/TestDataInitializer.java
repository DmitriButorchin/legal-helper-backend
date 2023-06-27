package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.agency.AgencyEntity;
import com.github.dmitributorchin.legal.helper.agency.AgencyRepository;
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
    private final AgencyRepository agencyRepository;
    private final RegionRepository regionRepository;

    @Override
    public void run(ApplicationArguments args) {
        agencyRepository.saveAll(List.of(
                createAgency("Местный Суд"),
                createAgency("Верховный Суд")
        ));

        regionRepository.saveAll(List.of(
                createRegion("Северный"),
                createRegion("Южный")
        ));
    }

    private AgencyEntity createAgency(String title) {
        var entity = new AgencyEntity();
        entity.setTitle(title);
        return entity;
    }

    private RegionEntity createRegion(String title) {
        var entity = new RegionEntity();
        entity.setTitle(title);
        return entity;
    }
}
