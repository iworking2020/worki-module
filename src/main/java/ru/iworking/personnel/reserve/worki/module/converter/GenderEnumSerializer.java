package ru.iworking.personnel.reserve.worki.module.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import ru.iworking.personnel.reserve.worki.module.dto.GenderEnum;

import java.math.BigDecimal;

public class GenderEnumSerializer extends StdConverter<GenderEnum, String> {

    @Override
    public String convert(GenderEnum value) {
        return value.getSystemName();
    }
}
