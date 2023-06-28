package com.github.dmitributorchin.legal.helper.lawyer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LawyerService {
    private final LawyerRepository lawyerRepository;

    public List<GetLawyers> getAllLawyers() {
        return lawyerRepository.findAll()
                .stream()
                .map(entity -> new GetLawyers(
                        entity.getId().toString(),
                        entity.getSsn(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getRegion().getId().toString(),
                        entity.getClaimCount()
                ))
                .collect(Collectors.toList());
    }

    public GetLawyer getLawyer(String id) {
        var entity = lawyerRepository.findById(UUID.fromString(id)).get();
        return new GetLawyer(
                entity.getId().toString(),
                entity.getSsn(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getClaimCount()
        );
    }
}
