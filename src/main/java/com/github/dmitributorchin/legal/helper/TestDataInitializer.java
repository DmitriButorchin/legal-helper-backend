package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.agency.AgencyEntity;
import com.github.dmitributorchin.legal.helper.agency.AgencyRepository;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerEntity;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerRepository;
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
    private final LawyerRepository lawyerRepository;

    @Override
    public void run(ApplicationArguments args) {
        agencyRepository.saveAll(List.of(
                createAgency("Местный Суд"),
                createAgency("Верховный Суд")
        ));

        var northRegion = createRegion("Северный");
        var southRegion = createRegion("Южный");
        regionRepository.saveAll(List.of(northRegion, southRegion));

        lawyerRepository.saveAll(List.of(
                createLawyer("A101", "Алексей", "Иванов", northRegion),
                createLawyer("A102", "Иван", "Петров", northRegion),
                createLawyer("B101", "Елена", "Козлова", southRegion),
                createLawyer("B102", "Игорь", "Алексеев", southRegion),
                createLawyer("B103", "Федор", "Гром", southRegion)
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

    private LawyerEntity createLawyer(String ssn, String firstName, String lastName, RegionEntity region) {
        var entity = new LawyerEntity();
        entity.setSsn(ssn);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setRegion(region);
        return entity;
    }
}
