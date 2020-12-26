package ru.iworking.personnel.reserve.worki.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatePage {
    protected List<CandidateDto> candidates;
    protected CandidatePageMetaDto meta;
}


