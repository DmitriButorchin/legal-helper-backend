package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.agency.AgencyEntity;
import com.github.dmitributorchin.legal.helper.agency.AgencyRepository;
import com.github.dmitributorchin.legal.helper.claim.ClaimService;
import com.github.dmitributorchin.legal.helper.claim.CreateClaim;
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
    private final ClaimService claimService;

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

        claimService.createClaim(createClaim("1", localCourt, northRegion, alex));
        claimService.createClaim(createClaim("2", supremeCourt, northRegion, ivan));
        claimService.createClaim(createClaim("3", localCourt, southRegion, helen));
        claimService.createClaim(createClaim("4", supremeCourt, southRegion, igor));
        claimService.createClaim(createClaim("5", supremeCourt, southRegion, fedor));
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

    private CreateClaim createClaim(String number, AgencyEntity agency, RegionEntity region, LawyerEntity lawyer) {
        return new CreateClaim(
                number,
                agency.getId().toString(),
                region.getId().toString(),
                lawyer.getId().toString()
        );
    }
}
