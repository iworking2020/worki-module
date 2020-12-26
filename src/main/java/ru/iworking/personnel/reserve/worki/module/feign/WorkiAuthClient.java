package ru.iworking.personnel.reserve.worki.module.feign;

import feign.Headers;
import feign.RequestLine;
import ru.iworking.personnel.reserve.worki.module.dto.SessionReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.SessionResDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationResDataDto;

public interface WorkiAuthClient {

    @RequestLine("POST /api/auth/verification_code")
    @Headers({"Content-Type: application/json"})
    VerificationResDataDto sendVerificationCode(VerificationReqDataDto data);

    @RequestLine("POST /api/auth/session")
    @Headers({"Content-Type: application/json"})
    SessionResDataDto createSession(SessionReqDataDto data);

}
