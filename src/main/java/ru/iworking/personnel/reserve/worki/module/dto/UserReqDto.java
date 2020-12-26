package ru.iworking.personnel.reserve.worki.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {
    @JsonProperty("code")
    protected String code;
    @JsonProperty("verification_code_id")
    protected Long verificationCodeId;
}
