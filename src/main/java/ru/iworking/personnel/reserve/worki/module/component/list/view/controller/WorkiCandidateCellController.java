package ru.iworking.personnel.reserve.worki.module.component.list.view.controller;

import com.google.common.base.Strings;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.iworking.personnel.reserve.worki.module.component.list.view.pane.WorkiCandidatePane;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateDto;
import ru.iworking.personnel.reserve.worki.module.dto.ProfessionDto;

import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WorkiCandidateCellController {

    private static final Boolean IS_BACKGROUND_LOADING = true;
    private static final String DEFAULT_IMAGE_URL = "https://hb.bizmrg.com/worki-production/static/closed_contact_165x165.png";

    private final WorkiCandidatePane resumePane;
    @Getter private CandidateDto candidate;

    public void setData(CandidateDto candidate) {
        this.candidate = candidate;
        String fio = String.format("%s %s", candidate.getLastName(), candidate.getFirstName());
        resumePane.getFioLabel().setText(fio);
        String professions = candidate.getPrefProfessions().parallelStream()
                .map(ProfessionDto::getTitle)
                .collect(Collectors.joining(", "));
        if (!Strings.isNullOrEmpty(professions)) {
            resumePane.getProfessionLabel().setText(professions);
        } else {
            resumePane.getProfessionLabel().setText("не указана");
        }
        this.setAvatar(candidate);
    }

    private void setAvatar(CandidateDto candidate) {
        String url = Objects.nonNull(candidate.getAvatar()) ?
                candidate.getAvatar().getMedium() :
                DEFAULT_IMAGE_URL;
        Image image = new Image(url, 150, 150, false,false, IS_BACKGROUND_LOADING);
        resumePane.getImageView().setImage(image);
    }

}
