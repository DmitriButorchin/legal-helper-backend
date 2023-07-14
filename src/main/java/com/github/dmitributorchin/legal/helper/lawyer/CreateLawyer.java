package com.github.dmitributorchin.legal.helper.lawyer;

import jakarta.validation.constraints.NotBlank;

public record CreateLawyer(
        @NotBlank
        String ssn,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String regionId
) {
}
