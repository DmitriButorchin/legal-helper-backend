package com.github.dmitributorchin.legal.helper.region;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public List<Region> getAllRegions() {
        return regionService.getAllRegions();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegionCreated createRegion(@RequestBody @Valid CreateRegion region) {
        return regionService.createRegion(region);
    }
}
