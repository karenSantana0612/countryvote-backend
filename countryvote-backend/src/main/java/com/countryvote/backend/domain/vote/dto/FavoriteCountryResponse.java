package com.countryvote.backend.domain.vote.dto;

public record FavoriteCountryResponse(
        String code,
        String name,
        String officialName,
        String capital,
        String region,
        String subregion,
        long votes
) {}
