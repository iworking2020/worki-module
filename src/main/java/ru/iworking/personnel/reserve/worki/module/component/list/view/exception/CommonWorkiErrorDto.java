package ru.iworking.personnel.reserve.worki.module.component.list.view.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonWorkiErrorDto {
    protected String message;
    protected String type;
}
