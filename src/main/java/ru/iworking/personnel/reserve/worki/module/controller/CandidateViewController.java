package ru.iworking.personnel.reserve.worki.module.controller;

import com.google.common.base.Strings;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateDto;
import ru.iworking.personnel.reserve.worki.module.dto.ExperienceDto;
import ru.iworking.personnel.reserve.worki.module.dto.NationalityDto;
import ru.iworking.personnel.reserve.worki.module.dto.ProfessionDto;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CandidateViewController implements Initializable {

    private static final Boolean IS_BACKGROUND_LOADING = true;
    private static final String DEFAULT_IMAGE_URL = "https://hb.bizmrg.com/worki-production/static/closed_contact_165x165.png";
    private static final String URL_CANDIDATE_PAGE_TEMPLATE = "https://hr.vkrabota.ru/candidate/%s/search";

    private final HostServices hostServices;

    @FXML private ImageView photoImageView;
    @FXML private Label fioLabel;
    @FXML private Label needProfessionsLabel;
    @FXML private Label ageLabel;
    @FXML private Label nationalLabel;
    @FXML private Label addressLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label languagesLabel;

    @FXML private Pane candidateViewPane;
    @FXML private TabPane candidateTabPane;
    @FXML private VBox experiencePane;

    private CandidateDto currentCandidate = null;
    private boolean isShow = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();
    }

    public void show() {
        isShow = true;
        candidateViewPane.setVisible(true);
        candidateViewPane.setManaged(true);
    }

    public boolean isShow() {
        return isShow;
    }

    public void show(AppFunctionalInterface function) {
        function.execute();
        this.show();
    }

    public void hide() {
        isShow = false;
        candidateViewPane.setVisible(false);
        candidateViewPane.setManaged(false);
    }

    public void hide(AppFunctionalInterface function) {
        function.execute();
        this.hide();
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        hide();
    }

    public void setData(CandidateDto candidate) {
        if (Objects.nonNull(candidate)) {
            clear();
            this.currentCandidate = candidate;
            String fio = String.format("%s %s", candidate.getLastName(), candidate.getFirstName());
            fioLabel.setText(fio);
            if (Objects.nonNull(candidate.getAge())) {
                String ageText = String.format("%s %s", candidate.getAge(), TextUtil.nameForYears(candidate.getAge()));
                ageLabel.setText(ageText);
            }
            String professions = candidate.getPrefProfessions().parallelStream()
                    .map(ProfessionDto::getTitle)
                    .collect(Collectors.joining(", "));
            if (!Strings.isNullOrEmpty(professions)) {
                needProfessionsLabel.setText(professions);
            } else {
                needProfessionsLabel.setText("не указана");
            }
            if (Objects.nonNull(candidate.getNationality())) {
                NationalityDto nationality = candidate.getNationality();
                nationalLabel.setText(nationality.getName());
            }
            addressLabel.setText(candidate.getAddress());
            descriptionLabel.setText(candidate.getDescription());
            languagesLabel.setText(candidate.getLanguages());
            candidate.getExperiences().stream()
                    .map(this::createExperienceBlock)
                    .filter(Objects::nonNull)
                    .forEach(pane -> {
                        if (!experiencePane.getChildren().isEmpty()) {
                            final Separator separator = new Separator();
                            experiencePane.getChildren().add(separator);
                        }
                        experiencePane.getChildren().add(pane);
                    });
            this.setAvatar(candidate);
        }
    }

    private Pane createExperienceBlock(ExperienceDto experience) {
        VBox pane = new VBox();
        pane.setSpacing(5.00);
        String profession = Objects.nonNull(experience.getProfession()) ?
                experience.getProfession().getTitle() :
                "профессия не указана";
        String firstLine = String.format("(%s) %s", experience.getPeriod(), profession);
        pane.getChildren().add(new Label(firstLine));
        pane.getChildren().add(new Label(experience.getTitle()));
        Label descLabel = new Label(experience.getDescription());
        descLabel.setWrapText(true);
        pane.getChildren().add(descLabel);
        return pane;
    }

    public void clear() {
        this.currentCandidate = null;
        experiencePane.getChildren().clear();
        candidateTabPane.getSelectionModel().select(0);
    }

    private void setAvatar(CandidateDto candidate) {
        String url = Objects.nonNull(candidate.getAvatar()) ?
                candidate.getAvatar().getMedium() :
                DEFAULT_IMAGE_URL;
        Image image = new Image(url, 150, 150, false,false, IS_BACKGROUND_LOADING);
        photoImageView.setImage(image);
    }

    @FXML
    public void openBrowser(ActionEvent event) {
        openBrowser(getUrlCandidate());
    }

    private void openBrowser(final String url) {
        hostServices.showDocument(url);
    }

    private String getUrlCandidate() {
        String url = String.format(URL_CANDIDATE_PAGE_TEMPLATE, this.currentCandidate.getId());
        return url;
    }

    @FXML
    public void copyURL(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(getUrlCandidate());
        clipboard.setContent(content);
    }

}
