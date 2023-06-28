package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.agency.AgencyEntity;
import com.github.dmitributorchin.legal.helper.agency.AgencyRepository;
import com.github.dmitributorchin.legal.helper.claim.ClaimEntity;
import com.github.dmitributorchin.legal.helper.claim.ClaimRepository;
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
    private final ClaimRepository claimRepository;

    @Override
    public void run(ApplicationArguments args) {
        var localCourt = createAgency("Местный Суд");
        var supremeCourt = createAgency("Верховный Суд");
        agencyRepository.saveAll(List.of(localCourt, supremeCourt));

        var northRegion = createRegion("Северный");
        var southRegion = createRegion("Южный");
        regionRepository.saveAll(List.of(northRegion, southRegion));

        var alex = createLawyer("A101", "Алексей", "Иванов", northRegion);
        var ivan = createLawyer("A102", "Иван", "Петров", northRegion);
        var helen = createLawyer("B101", "Елена", "Козлова", southRegion);
        var igor = createLawyer("B102", "Игорь", "Алексеев", southRegion);
        var fedor = createLawyer("B103", "Федор", "Гром", southRegion);
        lawyerRepository.saveAll(List.of(alex, ivan, helen, igor, fedor));

        claimRepository.saveAll(List.of(
                createClaim("1", localCourt, northRegion, alex),
                createClaim("2", supremeCourt, northRegion, ivan),
                createClaim("3", localCourt, southRegion, helen),
                createClaim("4", supremeCourt, southRegion, igor),
                createClaim("5", supremeCourt, southRegion, fedor)
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

    private ClaimEntity createClaim(String number, AgencyEntity agency, RegionEntity region, LawyerEntity lawyer) {
        var entity = new ClaimEntity();
        entity.setNumber(number);
        entity.setAgency(agency);
        entity.setRegion(region);
        entity.setLawyer(lawyer);
        return entity;
    }
}
