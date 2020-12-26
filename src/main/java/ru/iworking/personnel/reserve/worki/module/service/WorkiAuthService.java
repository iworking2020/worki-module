package ru.iworking.personnel.reserve.worki.module.service;

import ru.iworking.personnel.reserve.worki.module.dto.SessionReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.SessionResDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationResDataDto;

public interface WorkiAuthService {

    String getAuthHeader();

    VerificationResDataDto sendVerificationCode(VerificationReqDataDto data);

    SessionResDataDto createSession(SessionReqDataDto data);

}
