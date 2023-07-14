package com.github.dmitributorchin.legal.helper.lawyer;

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
public class LawyerService {
    private final LawyerRepository lawyerRepository;

    public List<GetLawyers> getAllLawyers() {
        return lawyerRepository.findAll()
                .stream()
                .map(entity -> new GetLawyers(
                        entity.getSsn(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getRegion().getId().toString(),
                        entity.getClaimCount()
                ))
                .collect(Collectors.toList());
    }

    public GetLawyer getLawyer(String id) {
        var entity = lawyerRepository.findById(id).get();
        return new GetLawyer(
                entity.getSsn(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getClaimCount()
        );
    }

    public LawyerCreated createLawyer(CreateLawyer lawyer) {
        var entity = new LawyerEntity();
        entity.setSsn(lawyer.ssn());
        entity.setFirstName(lawyer.firstName());
        entity.setLastName(lawyer.lastName());
        var region = new RegionEntity();
        region.setId(UUID.fromString(lawyer.regionId()));
        entity.setRegion(region);
        lawyerRepository.save(entity);

        return new LawyerCreated(
                entity.getSsn(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getRegion().getId().toString(),
                entity.getClaimCount()
        );
    }
}
