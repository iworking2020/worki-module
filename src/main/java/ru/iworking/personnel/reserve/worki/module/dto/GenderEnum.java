package ru.iworking.personnel.reserve.worki.module.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum  GenderEnum {
    ANY(0, "", "Любой пол"),
    MALE(1, "Male", "Мужской"),
    FEMALE(2, "Female", "Женский");

    @Getter private Integer innerCode;
    @Getter private String systemName;
    @Getter private String viewName;

    GenderEnum(Integer innerCode, String systemName, String viewName) {
        this.innerCode = innerCode;
        this.systemName = systemName;
        this.viewName = viewName;
    }

    public static GenderEnum getSystemName(String systemName) {
        if (Objects.isNull(systemName)) return null;
        final Map<String, GenderEnum> allCodeMap = Arrays.asList(GenderEnum.values()).parallelStream()
                .collect(Collectors.toMap(GenderEnum::getSystemName, Function.identity()));
        return allCodeMap.get(systemName);
    }

    public static GenderEnum getInnerCode(Integer code) {
        if (Objects.isNull(code)) return null;
        final Map<Integer, GenderEnum> allCodeMap = Arrays.asList(GenderEnum.values()).parallelStream()
                .collect(Collectors.toMap(GenderEnum::getInnerCode, Function.identity()));
        return allCodeMap.get(code);
    }
}
