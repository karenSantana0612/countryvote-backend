package com.countryvote.backend.domain.country.controller;

import com.countryvote.backend.domain.country.service.RestCountriesProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/countries")
public class CountryProxyController {

    private final RestCountriesProxyService restCountriesProxyservice;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getAllCountries(@RequestParam (required = false) String fields) {
        return restCountriesProxyservice.getCountriesAll(fields);
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getCountriesByName(
            @PathVariable String name,
            @RequestParam (required = false) Boolean fullText,
            @RequestParam (required = false) String fields
    ) {
        return restCountriesProxyservice.getCountriesByName(name, fullText, fields);
    }

    @GetMapping(value = "/region/{region}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> region(@PathVariable String region, @RequestParam(required = false) String fields) {
        return restCountriesProxyservice.getCountriesByRegion(region, fields);
    }
}

