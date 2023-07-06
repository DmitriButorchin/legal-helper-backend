package com.github.dmitributorchin.legal.helper.correspondent;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CorrespondentService {
    private final CorrespondentRepository correspondentRepository;

    public List<Correspondent> getAllCorrespondents() {
        return correspondentRepository.findAll()
                .stream()
                .map(entity -> new Correspondent(entity.getId().toString(), entity.getTitle()))
                .collect(Collectors.toList());
    }

    public CorrespondentCreated createCorrespondent(CreateCorrespondent correspondent) {
        var entity = new CorrespondentEntity();
        entity.setTitle(correspondent.title());
        correspondentRepository.save(entity);

        return new CorrespondentCreated(
                entity.getId().toString(),
                entity.getTitle()
        );
    }
}
