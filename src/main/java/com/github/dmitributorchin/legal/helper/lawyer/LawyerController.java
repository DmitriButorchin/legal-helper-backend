package com.github.dmitributorchin.legal.helper.lawyer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public GetLawyer getLawyer(@PathVariable String id) {
        return lawyerService.getLawyer(id);
    }
}
