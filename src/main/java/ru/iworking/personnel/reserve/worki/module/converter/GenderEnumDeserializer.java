package ru.iworking.personnel.reserve.worki.module.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import ru.iworking.personnel.reserve.worki.module.dto.GenderEnum;

public class GenderEnumDeserializer extends StdConverter<String, GenderEnum> {

    @Override
    public GenderEnum convert(String value) {
        return GenderEnum.getSystemName(value);
    }
}
