package com.countryvote.backend.domain.country.service;

import com.countryvote.backend.domain.country.dto.CountryDto;
import com.countryvote.backend.domain.country.dto.RestCountryResponse;

public final class CountryMapper {
    private CountryMapper() {}

    public static CountryDto toDto(RestCountryResponse response) {
        String commonName = null;
        if (response.name() != null && response.name().get("common") != null) {
            commonName = response.name().get("common").toString();
        }
        String flagPng = (response.flags() !=null) ? response.flags().get("png") : null;

        String code = (response.cca2() != null && !response.cca2().isBlank()) ? response.cca2() : response.cca3();

        return new CountryDto(code, commonName, response.region(), flagPng);
    }
}
