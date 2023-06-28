package com.github.dmitributorchin.legal.helper.lawyer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lawyers")
@RequiredArgsConstructor
public class LawyerController {
    private final LawyerService lawyerService;

    @GetMapping
    public List<Lawyer> getAllLawyers() {
        return lawyerService.getAllLawyers();
    }
}
