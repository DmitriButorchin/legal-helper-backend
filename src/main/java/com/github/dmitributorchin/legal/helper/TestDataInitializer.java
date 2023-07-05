package com.github.dmitributorchin.legal.helper;

import com.github.dmitributorchin.legal.helper.claim.ClaimService;
import com.github.dmitributorchin.legal.helper.claim.CreateClaim;
import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentEntity;
import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentRepository;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerEntity;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerRepository;
import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import com.github.dmitributorchin.legal.helper.region.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements ApplicationRunner {
    private final CorrespondentRepository correspondentRepository;
    private final RegionRepository regionRepository;
    private final LawyerRepository lawyerRepository;
    private final ClaimService claimService;

    @Override
    public void run(ApplicationArguments args) {
        var localCourt = createCorrespondent("Местный Суд");
        var supremeCourt = createCorrespondent("Верховный Суд");
        correspondentRepository.saveAll(List.of(localCourt, supremeCourt));

        var northRegion = createRegion("Северный");
        var southRegion = createRegion("Южный");
        regionRepository.saveAll(List.of(northRegion, southRegion));

        var alex = createLawyer("A101", "Алексей", "Иванов", northRegion);
        var ivan = createLawyer("A102", "Иван", "Петров", northRegion);
        var helen = createLawyer("B101", "Елена", "Козлова", southRegion);
        var igor = createLawyer("B102", "Игорь", "Алексеев", southRegion);
        var fedor = createLawyer("B103", "Федор", "Гром", southRegion);
        lawyerRepository.saveAll(List.of(alex, ivan, helen, igor, fedor));

        claimService.createClaim(createClaim("1", localCourt, "2023-06-01", "1", northRegion, alex));
        claimService.createClaim(createClaim("2", supremeCourt, "2023-06-02", "1", northRegion, ivan));
        claimService.createClaim(createClaim("3", localCourt, "2023-06-03", "2", southRegion, helen));
        claimService.createClaim(createClaim("4", supremeCourt, "2023-06-04", "2", southRegion, igor));
        claimService.createClaim(createClaim("5", supremeCourt, "2023-06-05", "3", southRegion, fedor));
    }

    private CorrespondentEntity createCorrespondent(String title) {
        var entity = new CorrespondentEntity();
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

    private CreateClaim createClaim(
            String number,
            CorrespondentEntity correspondent,
            String creationDate,
            String creationNumber,
            RegionEntity region,
            LawyerEntity lawyer
    ) {
        return new CreateClaim(
                number,
                correspondent.getId().toString(),
                LocalDate.parse(creationDate),
                creationNumber,
                "Обращение",
                "Артур Пирожков",
                region.getId().toString(),
                lawyer.getId().toString(),
                "НН",
                LocalDate.parse(creationDate).plus(15, ChronoUnit.DAYS)
        );
    }
}
