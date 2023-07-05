package com.github.dmitributorchin.legal.helper.claim;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimCreated createClaim(@RequestBody @Valid CreateClaim claim) {
        return claimService.createClaim(claim);
    }
}
