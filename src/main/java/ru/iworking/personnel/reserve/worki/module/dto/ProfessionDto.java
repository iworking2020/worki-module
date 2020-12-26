package ru.iworking.personnel.reserve.worki.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionDto {
    protected Long id;
    protected String title;
    @JsonProperty("available_for_temporary")
    protected Boolean availableForTemporary;

}
