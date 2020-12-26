package ru.iworking.personnel.reserve.worki.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NationalityDto {
    protected Long id;
    protected String name;
    protected Integer order;
}
