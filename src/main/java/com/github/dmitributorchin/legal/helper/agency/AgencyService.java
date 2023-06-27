package com.github.dmitributorchin.legal.helper.agency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final AgencyRepository agencyRepository;

    public List<Agency> getAllAgencies() {
        return agencyRepository.findAll()
                .stream()
                .map(entity -> new Agency(entity.getId().toString(), entity.getTitle()))
                .collect(Collectors.toList());
    }
}
