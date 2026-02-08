package com.countryvote.backend.domain.country.controller;

import com.countryvote.backend.domain.country.dto.CountryDto;
import com.countryvote.backend.domain.country.service.RestCountriesProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/countries")
public class CountryProxyController {

    private final RestCountriesProxyService restCountriesProxyService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CountryDto>> getAllCountries(@RequestParam (required = false) String fields) {
        return restCountriesProxyService.getCountriesAll(fields);
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CountryDto>> getCountriesByName(
            @PathVariable String name,
            @RequestParam (required = false) Boolean fullText,
            @RequestParam (required = false) String fields
    ) {
        return restCountriesProxyService.getCountriesByName(name, fullText, fields);
    }

    @GetMapping(value = "/region/{region}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CountryDto>> region(@PathVariable String region, @RequestParam(required = false) String fields) {
        return restCountriesProxyService.getCountriesByRegion(region, fields);
    }

    @GetMapping(value = "/alpha/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CountryDto>> getCountriesByCode(
            @PathVariable String code,
            @RequestParam(required = false) String fields
    ) {
        return restCountriesProxyService.getCountriesByCode(code, fields);
    }

    @GetMapping(value = "/alpha", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CountryDto>> getCountriesByCodes(
            @RequestParam String codes,
            @RequestParam(required = false) String fields
    ) {
        return restCountriesProxyService.getCountriesByCodes(codes, fields);
    }
}

