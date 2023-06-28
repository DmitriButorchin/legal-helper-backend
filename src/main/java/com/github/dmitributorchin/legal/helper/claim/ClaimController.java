package com.github.dmitributorchin.legal.helper.claim;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/claims")
@RequiredArgsConstructor
public class ClaimController {
    private final ClaimService claimService;

    @GetMapping
    public List<Claim> getAllClaims() {
        return claimService.getAllClaims();
    }
}
