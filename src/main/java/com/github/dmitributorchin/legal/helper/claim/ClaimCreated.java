package com.github.dmitributorchin.legal.helper.claim;

public record ClaimCreated(
        String registrationDate,
        String registrationNumber,
        String correspondentId,
        String creationDate,
        String creationNumber,
        String summary,
        String responsible,
        String regionId,
        String lawyerId,
        String defendant,
        String deadline
) {
}
