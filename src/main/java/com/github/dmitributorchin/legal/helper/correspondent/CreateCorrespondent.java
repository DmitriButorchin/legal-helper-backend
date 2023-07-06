package com.github.dmitributorchin.legal.helper.correspondent;

import jakarta.validation.constraints.NotBlank;

public record CreateCorrespondent(
        @NotBlank
        String title
) {
}
