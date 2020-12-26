package ru.iworking.personnel.reserve.worki.module.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.ApplicationPropertiesProvider;
import ru.iworking.personnel.reserve.worki.module.component.list.view.exception.WorkiErrorDecoder;
import ru.iworking.personnel.reserve.worki.module.config.WorkiModuleConfiguration;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateDto;
import ru.iworking.personnel.reserve.worki.module.dto.CandidatePage;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateSearchParamsDto;
import ru.iworking.personnel.reserve.worki.module.feign.CandidateClient;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final WorkiModuleConfiguration configuration;

    private CandidateClient candidateClient;
    private final WorkiAuthService authService;

    @Getter
    @Setter
    private CandidateSearchParamsDto searchParams = CandidateSearchParamsDto.builder()
            .page(1)
            .keyWord("")
            .build();

    private final LoadingCache<CandidateSearchParamsDto, CandidatePage> pages = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(
                    new CacheLoader<CandidateSearchParamsDto, CandidatePage>() {
                        @Override
                        public CandidatePage load(CandidateSearchParamsDto searchParamsDto) {
                            Map<String, Object> params = new ObjectMapper().convertValue(searchParamsDto, Map.class);
                            return candidateClient.findAll(authService.getAuthHeader(), params);
                        }
                    });

    @PostConstruct
    public void init() {
        candidateClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new WorkiErrorDecoder())
                .logger(new Slf4jLogger(CandidateClient.class))
                .logLevel(Logger.Level.FULL)
                .target(CandidateClient.class, configuration.getApiUrl());
    }

    @Override
    public List<CandidateDto> findAllByPage(Integer page) {
        Optional<CandidatePage> optionalCandidatePage = Optional.ofNullable(findPageByNumber(page));
        return optionalCandidatePage.isPresent() ?
                optionalCandidatePage.get().getCandidates() :
                Collections.emptyList();
    }

    @Override
    public CandidatePage findPageByNumber(Integer page) {
        searchParams.setPage(page);
        return findByParams(searchParams);
    }

    @Override
    public CandidatePage findByParams(CandidateSearchParamsDto params) {
        if (!searchParams.equals(params)) {
            this.searchParams = params;
        }
        try {
            return pages.get(params);
        } catch (ExecutionException e) {
            return null;
        }
    }

}
