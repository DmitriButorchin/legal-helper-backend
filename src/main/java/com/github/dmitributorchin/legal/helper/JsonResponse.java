package com.github.dmitributorchin.legal.helper;

import java.util.List;

public record JsonResponse(List<JsonError> errors) {
}
