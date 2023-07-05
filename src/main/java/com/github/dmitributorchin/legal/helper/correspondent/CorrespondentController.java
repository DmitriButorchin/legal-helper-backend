package com.github.dmitributorchin.legal.helper.correspondent;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/correspondents")
@RequiredArgsConstructor
public class CorrespondentController {
    private final CorrespondentService correspondentService;

    @GetMapping
    public List<Correspondent> getAllCorrespondents() {
        return correspondentService.getAllCorrespondents();
    }
}
