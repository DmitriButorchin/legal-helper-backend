package com.github.dmitributorchin.legal.helper.lawyer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LawyerService {
    private final LawyerRepository lawyerRepository;

    public List<Lawyer> getAllLawyers() {
        return lawyerRepository.findAll()
                .stream()
                .map(entity -> new Lawyer(
                        entity.getId().toString(),
                        entity.getSsn(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getRegion().getId().toString()
                ))
                .collect(Collectors.toList());
    }
}
