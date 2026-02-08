package com.countryvote.backend.domain.country.service;

import com.countryvote.backend.common.utils.FieldsUtil;
import com.countryvote.backend.domain.country.dto.CountryDto;
import com.countryvote.backend.domain.country.dto.RestCountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestCountriesProxyService {

    private final WebClient restCountriesClient;

    private static final String DEFAULT_FIELDS = "name,flags,cca2,cca3,region,capital,population";

    public Mono<List<CountryDto>> getCountriesAll(String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> uri.path("/all").queryParam("fields", f).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RestCountryResponse.class)
                .map(CountryMapper::toDto)
                .collectList();
    }

    public Mono<List<CountryDto>>  getCountriesByName(String name, Boolean fullText, String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> {
                    var b = uri.path("/name/{name}").queryParam("fields", f);
                    if (fullText != null) b = b.queryParam("textFull", fullText);
                    return b.build(name);
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RestCountryResponse.class)
                .map(CountryMapper::toDto)
                .collectList();
    }

    public Mono<List<CountryDto>> getCountriesByRegion(String region, String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> uri.path("/region/{region}").queryParam("fields", f).build(region))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RestCountryResponse.class)
                .map(CountryMapper::toDto)
                .collectList();
    }

    public Mono<List<CountryDto>>  getCountriesByCode(String code, String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> uri.path("/alpha/{code}")
                        .queryParam("fields", f)
                        .build(code))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RestCountryResponse.class)
                .map(CountryMapper::toDto)
                .collectList();
    }

    public Mono<List<CountryDto>> getCountriesByCodes(String codes, String fields) {
        String f = FieldsUtil.normalizeFieldName(fields, DEFAULT_FIELDS);

        return restCountriesClient.get()
                .uri(uri -> uri.path("/alpha")
                        .queryParam("codes", codes)
                        .queryParam("fields", f)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RestCountryResponse.class)
                .map(CountryMapper::toDto)
                .collectList();
    }


}
