package ru.iworking.personnel.reserve.worki.module.service;

import com.google.common.base.Strings;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.worki.module.component.list.view.exception.WorkiErrorDecoder;
import ru.iworking.personnel.reserve.worki.module.config.WorkiModuleConfiguration;
import ru.iworking.personnel.reserve.worki.module.dto.SessionReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.SessionResDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationResDataDto;
import ru.iworking.personnel.reserve.worki.module.feign.WorkiAuthClient;

import javax.annotation.PostConstruct;
@Service
@RequiredArgsConstructor
public class WorkiAuthServiceImpl implements WorkiAuthService {

    private final WorkiModuleConfiguration configuration;

    private WorkiAuthClient client;

    private String jwtToken;

    @Override
    public String getAuthHeader() {
        if (Strings.isNullOrEmpty(jwtToken)) {
            return "";
        }
        return String.format("Worki %s", jwtToken);
    }

    @PostConstruct
    public void init() {
        client = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new WorkiErrorDecoder())
                .logger(new Slf4jLogger(WorkiAuthClient.class))
                .logLevel(Logger.Level.FULL)
                .target(WorkiAuthClient.class, configuration.getApiUrl());
    }

    @Override
    public VerificationResDataDto sendVerificationCode(VerificationReqDataDto data) {
        return client.sendVerificationCode(data);
    }

    @Override
    public SessionResDataDto createSession(SessionReqDataDto data) {
        SessionResDataDto res = client.createSession(data);
        jwtToken = res.getJwt();
        return res;
    }

}
