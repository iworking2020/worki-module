package ru.iworking.personnel.reserve.worki.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDto {
    protected Long id;
    @JsonProperty("content_type")
    protected String contentType;
    protected String small;
    protected String medium;
    protected String original;
}
