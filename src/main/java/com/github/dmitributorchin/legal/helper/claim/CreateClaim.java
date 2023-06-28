package com.github.dmitributorchin.legal.helper.claim;

import jakarta.validation.constraints.NotBlank;

public record CreateClaim(
        @NotBlank
        String number,

        @NotBlank
        String agencyId,

        @NotBlank
        String regionId,

        @NotBlank
        String lawyerId
) {
}
