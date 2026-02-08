package com.countryvote.backend.domain.vote.service;

import com.countryvote.backend.domain.vote.dto.FavoriteCountryResponse;
import com.countryvote.backend.domain.vote.repository.UserVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserVoteRepository userVoteRepository;
    private final WebClient webClient;

    private static final String FIELDS = "name,capital,region,subregion,cca2,cca3";

    @Transactional(readOnly = true)
    public Mono<List<FavoriteCountryResponse>> top10() {

        List<Object[]> grouped = userVoteRepository.countVotesGrouped();

        List<Map.Entry<String, Long>> top = grouped.stream()
                .map(r -> Map.entry((String) r[0], (Long) r[1]))
                .limit(10)
                .toList();

        if (top.isEmpty()) return Mono.just(List.of());

        String codesCsv = top.stream().map(Map.Entry::getKey).collect(Collectors.joining(","));

        Map<String, Long> votesByCode = top.stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().toUpperCase(),
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        return webClient.get()
                .uri(uri -> uri.path("/alpha")
                        .queryParam("codes", codesCsv)
                        .queryParam("fields", FIELDS)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(com.countryvote.backend.domain.country.dto.RestCountryResponse.class)
                .map(rc -> {
                    String common = rc.name() != null && rc.name().get("common") != null
                            ? rc.name().get("common").toString()
                            : null;

                    String official = rc.name() != null && rc.name().get("official") != null
                            ? rc.name().get("official").toString()
                            : null;

                    String capital = (rc.capital() != null && !rc.capital().isEmpty())
                            ? rc.capital().get(0)
                            : null;

                    String code = (rc.cca2() != null && !rc.cca2().isBlank()) ? rc.cca2() : rc.cca3();

                    long votes = votesByCode.getOrDefault(code != null ? code.toUpperCase() : "", 0L);

                    return new FavoriteCountryResponse(
                            code,
                            common,
                            official,
                            capital,
                            rc.region(),
                            rc.subregion(),
                            votes
                    );
                })
                .sort((a, b) -> Long.compare(b.votes(), a.votes()))
                .collectList();
    }
}
