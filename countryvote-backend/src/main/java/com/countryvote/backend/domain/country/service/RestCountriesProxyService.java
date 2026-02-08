package com.countryvote.backend.domain.country.service;

import com.countryvote.backend.common.utils.FieldsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class RestCountriesProxyService {

    private final WebClient restCountriesClient;

    private static final String DEFAULT_FIELDS = "name,flags,cca2,cca3,region,capital,population";

    public Mono<String> getCountriesAll(String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> uri.path("/all").queryParam("fields", f).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String>  getCountriesByName(String name, Boolean fullText, String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> {
                    var b = uri.path("/name/{name}").queryParam("fields", f);
                    if (fullText != null) b = b.queryParam("text", fullText);
                    return b.build(name);
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getCountriesByRegion(String region, String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> uri.path("/region/{region}").queryParam("fields", f).build(region))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }


}
