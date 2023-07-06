package com.github.dmitributorchin.legal.helper.region;

import jakarta.validation.constraints.NotBlank;

public record CreateRegion(
        @NotBlank
        String title
) {
}
