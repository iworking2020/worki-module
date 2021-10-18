package ru.iworking.personnel.reserve.worki.module.feign;

import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import ru.iworking.personnel.reserve.worki.module.dto.CandidatePage;

import java.util.Map;

public interface CandidateClient {

    @RequestLine("GET /api/web/v1/candidates")
    @Headers({"Content-Type: application/json", "Authorization: {authHeader}"})
    CandidatePage findAll(@Param("authHeader") String authHeader, @QueryMap(encoded = true) Map<String, Object> params);

}
