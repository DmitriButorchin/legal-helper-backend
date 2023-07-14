package com.github.dmitributorchin.legal.helper.lawyer;

public record LawyerCreated(
        String ssn,
        String firstName,
        String lastName,
        String regionId,
        int claimCount
) {
}
