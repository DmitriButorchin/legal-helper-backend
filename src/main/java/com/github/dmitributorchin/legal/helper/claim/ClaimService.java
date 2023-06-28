package com.github.dmitributorchin.legal.helper.claim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimService {
    private final ClaimRepository claimRepository;

    public List<Claim> getAllClaims() {
        return claimRepository.findAll()
                .stream()
                .map(entity -> new Claim(
                        entity.getId().toString(),
                        entity.getNumber(),
                        entity.getAgency().getId().toString(),
                        entity.getRegion().getId().toString(),
                        entity.getLawyer().getId().toString()
                ))
                .collect(Collectors.toList());
    }
}
