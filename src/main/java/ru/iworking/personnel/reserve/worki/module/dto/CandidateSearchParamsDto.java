package ru.iworking.personnel.reserve.worki.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iworking.personnel.reserve.worki.module.converter.GenderEnumDeserializer;
import ru.iworking.personnel.reserve.worki.module.converter.GenderEnumSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateSearchParamsDto {
    @JsonProperty("page")
    protected Integer page;
    @JsonProperty("key_word")
    protected String keyWord;
    @JsonProperty("address")
    protected String address;
    @JsonProperty("distance_to")
    protected Integer distance;
    @JsonProperty("month_of_experience")
    protected Integer monthExperience;
    @JsonSerialize(converter = GenderEnumSerializer.class)
    @JsonDeserialize(converter = GenderEnumDeserializer.class)
    @JsonProperty("sex")
    protected GenderEnum gender;
    @JsonProperty("age_from")
    protected Integer minAge;
    @JsonProperty("age_to")
    protected Integer maxAge;
}
