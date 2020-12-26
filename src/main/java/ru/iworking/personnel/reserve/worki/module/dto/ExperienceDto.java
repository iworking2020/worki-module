package ru.iworking.personnel.reserve.worki.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {
    protected String title;
    protected String description;
    protected Integer duration;
    protected Boolean current;
    protected ProfessionDto profession;
}
