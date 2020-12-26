package ru.iworking.personnel.reserve.worki.module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResDataDto {
    protected String jwt;
    protected UserResDto user;
    protected MetaResDto meta;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserResDto {
    @JsonProperty("id")
    protected Long id;
    @JsonProperty("first_name")
    protected String firstName;
    @JsonProperty("last_name")
    protected String lastName;
    @JsonProperty("user_type")
    protected String userType;
    @JsonProperty("need_registration")
    protected Boolean needRegistration;
    @JsonProperty("lat")
    protected String lat;
    @JsonProperty("long")
    protected String longs;
    @JsonProperty("email")
    protected String email;
    @JsonProperty("confirmed")
    protected Boolean confirmed;
    @JsonProperty("experiments")
    protected List<String> experiments;
    @JsonProperty("email_sendable")
    protected Boolean emailSendable;
    @JsonProperty("avatar")
    protected String avatar;
    @JsonProperty("nationality")
    protected String nationality;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class MetaResDto {
    @JsonProperty("new_messages")
    protected Integer newMessages;
    @JsonProperty("new_applications")
    protected Integer newApplications;
    @JsonProperty("new_job_searches")
    protected Integer newJobSearches;
    @JsonProperty("submitted_applications")
    protected Integer submittedApplications;
    @JsonProperty("new_profile_views")
    protected Integer newProfileViews;
}
