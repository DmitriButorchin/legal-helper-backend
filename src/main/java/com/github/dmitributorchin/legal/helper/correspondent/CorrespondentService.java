package com.github.dmitributorchin.legal.helper.correspondent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CorrespondentService {
    private final CorrespondentRepository correspondentRepository;

    public List<Correspondent> getAllCorrespondents() {
        return correspondentRepository.findAll()
                .stream()
                .map(entity -> new Correspondent(entity.getId().toString(), entity.getTitle()))
                .collect(Collectors.toList());
    }
}
