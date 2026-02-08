package com.countryvote.backend.domain.vote.controller;

import com.countryvote.backend.domain.vote.dto.FavoriteCountryResponse;
import com.countryvote.backend.domain.vote.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoritesController {

    private final FavoriteService favoriteService;

    @GetMapping(value = "/top10", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<FavoriteCountryResponse>> top10() {
        return favoriteService.top10();
    }
}
