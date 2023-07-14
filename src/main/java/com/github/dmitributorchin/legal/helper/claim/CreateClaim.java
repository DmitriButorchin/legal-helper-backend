package com.github.dmitributorchin.legal.helper.claim;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateClaim(
        @NotBlank
        String registrationNumber,

        @NotBlank
        String correspondentId, // TODO: validation

        @NotNull
        LocalDate creationDate,

        @NotBlank
        String creationNumber, // TODO: validation

        @NotBlank
        String summary,

        @NotBlank
        String responsible,

        @NotBlank
        String regionId, // TODO: validation

        @NotBlank
        String lawyerSsn, // TODO: validation

        @NotBlank
        String defendant,

        @NotNull
        LocalDate deadline
) {
}
