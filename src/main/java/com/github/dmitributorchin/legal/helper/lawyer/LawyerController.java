package com.github.dmitributorchin.legal.helper.lawyer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lawyers")
@RequiredArgsConstructor
public class LawyerController {
    private final LawyerService lawyerService;

    @GetMapping
    public List<GetLawyers> getAllLawyers() {
        return lawyerService.getAllLawyers();
    }

    @GetMapping("/{ssn}")
    public GetLawyer getLawyer(@PathVariable String ssn) {
        return lawyerService.getLawyer(ssn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LawyerCreated createLawyer(@RequestBody @Valid CreateLawyer lawyer) {
        return lawyerService.createLawyer(lawyer);
    }
}
