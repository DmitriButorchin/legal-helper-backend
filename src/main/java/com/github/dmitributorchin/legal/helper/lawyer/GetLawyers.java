package com.github.dmitributorchin.legal.helper.lawyer;

public record GetLawyers(String id, String ssn, String firstName, String lastName, String regionId, int claimCount) {
}
