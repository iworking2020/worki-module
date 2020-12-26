package ru.iworking.personnel.reserve.worki.module.service;

import ru.iworking.personnel.reserve.worki.module.dto.CandidateDto;
import ru.iworking.personnel.reserve.worki.module.dto.CandidatePage;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateSearchParamsDto;

import java.util.List;

public interface CandidateService {

    CandidateSearchParamsDto getSearchParams();
    void setSearchParams(CandidateSearchParamsDto params);

    List<CandidateDto> findAllByPage(Integer page);

    CandidatePage findPageByNumber(Integer number);

    CandidatePage findByParams(CandidateSearchParamsDto params);

}
