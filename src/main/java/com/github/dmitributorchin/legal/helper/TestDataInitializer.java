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

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements ApplicationRunner {
    private final CorrespondentRepository correspondentRepository;
    private final RegionRepository regionRepository;
    private final LawyerRepository lawyerRepository;
    private final ClaimService claimService;

    @Override
    public void run(ApplicationArguments args) {
        var correspondent = createCorrespondent();
        correspondentRepository.save(correspondent);

        var region = createRegion();
        regionRepository.save(region);

        var lawyer = createLawyer(region);
        lawyerRepository.save(lawyer);

        var claim = createClaim(correspondent, region, lawyer);
        claimService.createClaim(claim);
    }

    private CorrespondentEntity createCorrespondent() {
        var entity = new CorrespondentEntity();
        entity.setTitle("Верховный Суд");
        return entity;
    }

    private RegionEntity createRegion() {
        var entity = new RegionEntity();
        entity.setTitle("Северный");
        return entity;
    }

    private LawyerEntity createLawyer(RegionEntity region) {
        var entity = new LawyerEntity();
        entity.setSsn("A101");
        entity.setFirstName("Алексей");
        entity.setLastName("Иванов");
        entity.setRegion(region);
        return entity;
    }

    private CreateClaim createClaim(CorrespondentEntity correspondent, RegionEntity region, LawyerEntity lawyer) {
        return new CreateClaim(
                "1",
                correspondent.getId().toString(),
                LocalDate.parse("2023-06-01"),
                "1",
                "Обращение",
                "Артур Пирожков",
                region.getId().toString(),
                lawyer.getSsn(),
                "НН",
                LocalDate.parse("2023-06-16")
        );
    }
}
