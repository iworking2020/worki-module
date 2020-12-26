package ru.iworking.personnel.reserve.worki.module.component.list.view.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import ru.iworking.personnel.reserve.exeption.FeignError;
import ru.iworking.personnel.reserve.exeption.FeignErrorUnprocessableEntity;

import java.io.InputStream;
import java.util.stream.Collectors;

public class WorkiErrorDecoder implements ErrorDecoder {
    @SneakyThrows
    @Override
    public FeignError decode(String s, Response response) {
        InputStream body = response.body().asInputStream();
        GlobalWorkiErrorDto errorDto = new ObjectMapper().readValue(body, GlobalWorkiErrorDto.class);
        String errorMsg = errorDto.getErrors().parallelStream()
                .map(MinWorkiErrorDto::getTitle)
                .collect(Collectors.joining(","));
        if (Integer.valueOf(response.status()).equals(422)) return new FeignErrorUnprocessableEntity(errorMsg);
        return new FeignError(response.status(), errorMsg);
    }
}
