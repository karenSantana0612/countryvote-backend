package com.countryvote.backend.domain.country.dto;

public record CountryDto (
        String code,
        String name,
        String region,
        String flagPng
) { }
