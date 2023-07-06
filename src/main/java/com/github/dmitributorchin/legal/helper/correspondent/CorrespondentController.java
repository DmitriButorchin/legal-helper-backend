package com.github.dmitributorchin.legal.helper.correspondent;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CorrespondentCreated createCorrespondent(@RequestBody @Valid CreateCorrespondent correspondent) {
        return correspondentService.createCorrespondent(correspondent);
    }
}
