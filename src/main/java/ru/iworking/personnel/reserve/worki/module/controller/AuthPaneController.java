package ru.iworking.personnel.reserve.worki.module.controller;

import com.google.common.base.Strings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationPropertiesProvider;
import ru.iworking.personnel.reserve.MessageProvider;
import ru.iworking.personnel.reserve.PrimaryStageProvider;
import ru.iworking.personnel.reserve.worki.module.converter.NumberFormatter;
import ru.iworking.personnel.reserve.worki.module.dto.SessionReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.UserReqDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationReqDataDto;
import ru.iworking.personnel.reserve.worki.module.dto.VerificationResDataDto;
import ru.iworking.personnel.reserve.exeption.FeignError;
import ru.iworking.personnel.reserve.worki.module.service.WorkiAuthService;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class AuthPaneController implements Initializable {

    private static final String SYSTEM_NAME_NUMBER_PHONE = "workiModuleNumberPhone";

    @FXML private TextField numberTextField;
    @FXML private TextField codeTextField;
    @FXML private Button acceptCodeButton;
    @FXML private Pane acceptCodePane;
    @FXML private Pane parentPane;

    private final MessageProvider messageProvider;
    private final ApplicationPropertiesProvider applicationPropertiesProvider;
    private final PrimaryStageProvider primaryStageProvider;
    private final WorkiAuthService workiAuthService;
    private final CandidatesPaneController candidatesPaneController;

    private VerificationResDataDto verificationResData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideAcceptCodeBlock();
        numberTextField.setTextFormatter(new NumberFormatter());
        codeTextField.setTextFormatter(new NumberFormatter());
        String numberPhone = applicationPropertiesProvider.getValueByName(SYSTEM_NAME_NUMBER_PHONE);
        if (!Strings.isNullOrEmpty(numberPhone)) numberTextField.setText(numberPhone);
    }

    @FXML
    public void sendCode(ActionEvent event) {
        applicationPropertiesProvider.saveValue(SYSTEM_NAME_NUMBER_PHONE, numberTextField.getText());
        VerificationReqDataDto req = new VerificationReqDataDto();
        req.setPhone(numberTextField.getText());
        try {
            primaryStageProvider.getPrimaryStage().getScene().setCursor(Cursor.WAIT);
            verificationResData = workiAuthService.sendVerificationCode(req);
            showAcceptCodeBlock();
        } catch (FeignError error) {
            String msg = String.format("Ошибка со статусом %s: %s", error.getStatus(), error.getMessage());
            messageProvider.sendMessage(msg);
        } finally {
            primaryStageProvider.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
        }
    }

    @FXML
    public void acceptCode(ActionEvent event) {
        if (Objects.nonNull(verificationResData)) {
            SessionReqDataDto req = new SessionReqDataDto();
            UserReqDto user = new UserReqDto();
            user.setCode(Objects.nonNull(codeTextField) ? codeTextField.getText() : "");
            user.setVerificationCodeId(verificationResData.getVerificationCodeId());
            req.setUser(user);
            try {
                primaryStageProvider.getPrimaryStage().getScene().setCursor(Cursor.WAIT);
                workiAuthService.createSession(req);
                candidatesPaneController.initData();
                hideAuthPane();
            } catch (FeignError error) {
                String msg = String.format("Ошибка со статусом %s: %s", error.getStatus(), error.getMessage());
                messageProvider.sendMessage(msg);
            } finally {
                primaryStageProvider.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
            }
        }
    }

    private void hideAcceptCodeBlock() {
        codeTextField.setDisable(true);
        acceptCodeButton.setDisable(true);
    }

    private void showAcceptCodeBlock() {
        codeTextField.setDisable(false);
        acceptCodeButton.setDisable(false);
    }

    private void hideAuthPane() {
        parentPane.setManaged(false);
        parentPane.setVisible(false);
    }

    private void showAuthPane() {
        parentPane.setManaged(true);
        parentPane.setVisible(true);
    }
}
