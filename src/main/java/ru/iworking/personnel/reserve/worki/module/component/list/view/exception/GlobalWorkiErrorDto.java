package ru.iworking.personnel.reserve.worki.module.component.list.view.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalWorkiErrorDto {
    protected String error;
    protected List<MinWorkiErrorDto> errors;
    @JsonProperty("common_error")
    protected CommonWorkiErrorDto commonError;
    @JsonProperty("field_errors")
    protected List<String> fieldErrors;
}
