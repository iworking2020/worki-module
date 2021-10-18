package ru.iworking.personnel.reserve.worki.module.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import ru.iworking.personnel.reserve.converter.DateTimeDeserializer;
import ru.iworking.personnel.reserve.converter.DateTimeSerializer;
import ru.iworking.personnel.reserve.converter.LocalDateDeserializer;
import ru.iworking.personnel.reserve.converter.LocalDateSerializer;
import ru.iworking.personnel.reserve.utils.TimeUtil;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {
    protected String id;
    @JsonProperty("first_name")
    protected String firstName;
    @JsonProperty("last_name")
    protected String lastName;
    protected String description;
    @JsonProperty("lat")
    protected Long lat;
    @JsonProperty("long")
    protected Long longs;
    protected Integer age;
    protected String address;
    protected String education;
    protected String languages;
    protected Boolean online;
    @JsonProperty("last_online_at")
    @JsonSerialize(converter = DateTimeSerializer.class)
    @JsonDeserialize(converter = DateTimeDeserializer.class)
    protected DateTime lastOnlineAt;
    @JsonProperty("created_at")
    @JsonSerialize(converter = DateTimeSerializer.class)
    @JsonDeserialize(converter = DateTimeDeserializer.class)
    protected DateTime createdAt;
    @JsonProperty("updated_at")
    @JsonSerialize(converter = DateTimeSerializer.class)
    @JsonDeserialize(converter = DateTimeDeserializer.class)
    protected DateTime updatedAt;
    @JsonSerialize(converter = LocalDateSerializer.class)
    @JsonDeserialize(converter = LocalDateDeserializer.class)
    protected LocalDate birthday;
    @JsonProperty("views_count")
    protected Long viewsCount;
    @JsonProperty("driving_license_categories")
    protected List<String> drivingLicenseCategories;
    @JsonProperty("medical_record")
    protected Boolean medicalRecord;
    @JsonProperty("candidate_status")
    protected String candidateStatus;
    @JsonProperty("active_candidate")
    protected Boolean activeCandidate;
    protected String comment;
    @JsonProperty("comment_count")
    protected Long commentCount;
    @JsonProperty("has_vk_client")
    protected Boolean hasVkClient;
    protected List<ExperienceDto> experiences;
    @JsonProperty("already_contacted")
    protected Boolean alreadyContacted;
    @JsonProperty("highlighted_till")
    protected String highlightedTill;
    protected AvatarDto avatar;
    protected BackgroundDto background;
    @JsonProperty("email_sendable")
    protected Boolean emailSendable;
    protected Boolean banned;
    protected Boolean deleted;
    @JsonProperty("disability_group")
    protected Integer disabilityGroup;
    protected List<ProfessionDto> professions;
    @JsonProperty("preferred_professions")
    protected List<ProfessionDto> prefProfessions;
    protected NationalityDto nationality;

}

