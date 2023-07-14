package com.github.dmitributorchin.legal.helper.claim;

import com.github.dmitributorchin.legal.helper.DomainException;
import com.github.dmitributorchin.legal.helper.JsonErrorSource;
import com.github.dmitributorchin.legal.helper.correspondent.CorrespondentEntity;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerRepository;
import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final LawyerRepository lawyerRepository;

    public List<Claim> getAllClaims() {
        return claimRepository.findAll()
                .stream()
                .map(this::toClaim)
                .collect(Collectors.toList());
    }

    private Claim toClaim(ClaimEntity entity) {
        return new Claim(
                entity.getRegistrationNumber(),
                entity.getCorrespondent().getId().toString(),
                entity.getRegion().getId().toString(),
                entity.getLawyer().getSsn()
        );
    }

    public ClaimCreated createClaim(CreateClaim claim) {
        if (claimRepository.existsById(claim.registrationNumber())) {
            var errorSource = new JsonErrorSource("/createClaim/registrationNumber");
            throw new DomainException("Claim with specified registration number already exists", errorSource);
        }

        var entity = new ClaimEntity();
        entity.setRegistrationNumber(claim.registrationNumber());
        var correspondent = new CorrespondentEntity();
        correspondent.setId(UUID.fromString(claim.correspondentId()));
        entity.setCorrespondent(correspondent);
        entity.setCreationDate(claim.creationDate());
        entity.setCreationNumber(claim.creationNumber());
        entity.setSummary(claim.summary());
        entity.setResponsible(claim.responsible());
        var region = new RegionEntity();
        region.setId(UUID.fromString(claim.regionId()));
        entity.setRegion(region);
        var lawyer = lawyerRepository.findById(claim.lawyerSsn()).get();
        lawyer.setClaimCount(lawyer.getClaimCount() + 1);
        entity.setLawyer(lawyer);
        entity.setDefendant(claim.defendant());
        entity.setDeadline(claim.deadline());
        claimRepository.save(entity);

        return new ClaimCreated(
                entity.getRegistrationDate().format(DateTimeFormatter.ISO_DATE),
                entity.getRegistrationNumber(),
                entity.getCorrespondent().getId().toString(),
                entity.getCreationDate().format(DateTimeFormatter.ISO_DATE),
                entity.getCreationNumber(),
                entity.getSummary(),
                entity.getResponsible(),
                entity.getRegion().getId().toString(),
                entity.getLawyer().getSsn(),
                entity.getDefendant(),
                entity.getDeadline().format(DateTimeFormatter.ISO_DATE)
        );
    }
}
