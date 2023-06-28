package com.github.dmitributorchin.legal.helper.claim;

import com.github.dmitributorchin.legal.helper.agency.AgencyEntity;
import com.github.dmitributorchin.legal.helper.lawyer.LawyerRepository;
import com.github.dmitributorchin.legal.helper.region.RegionEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Claim createClaim(CreateClaim claim) {
        var entity = new ClaimEntity();
        entity.setNumber(claim.number());
        var agency = new AgencyEntity();
        agency.setId(UUID.fromString(claim.agencyId()));
        entity.setAgency(agency);
        var region = new RegionEntity();
        region.setId(UUID.fromString(claim.regionId()));
        entity.setRegion(region);
        var lawyer = lawyerRepository.findById(UUID.fromString(claim.lawyerId())).get();
        lawyer.setClaimCount(lawyer.getClaimCount() + 1);
        entity.setLawyer(lawyer);
        claimRepository.save(entity);
        return toClaim(entity);
    }

    private Claim toClaim(ClaimEntity entity) {
        return new Claim(
                entity.getId().toString(),
                entity.getNumber(),
                entity.getAgency().getId().toString(),
                entity.getRegion().getId().toString(),
                entity.getLawyer().getId().toString()
        );
    }
}
