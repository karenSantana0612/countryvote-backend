package com.countryvote.backend.domain.country.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestCountryResponse(
        String cca2,
        String cca3,
        Map<String, Object> name,
        List<String> capital,
        String region,
        String subregion,
        Map<String, String> flags
) {}
