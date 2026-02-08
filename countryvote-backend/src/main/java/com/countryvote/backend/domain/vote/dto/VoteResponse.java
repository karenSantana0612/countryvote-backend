package com.countryvote.backend.domain.vote.dto;

import java.time.Instant;

public record VoteResponse (
        Long id,
        String email,
        String countryCode,
        Instant createAt
) {}