package ru.iworking.personnel.reserve.worki.module.controller;

import com.google.common.base.Strings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.ApplicationPropertiesProvider;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.personnel.reserve.worki.module.component.list.view.cell.WorkiCandidateCell;
import ru.iworking.personnel.reserve.worki.module.component.list.view.factory.WorkiCandidateCellControllerFactory;
import ru.iworking.personnel.reserve.worki.module.converter.AgeFormatter;
import ru.iworking.personnel.reserve.worki.module.converter.GenderCellFactory;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateDto;
import ru.iworking.personnel.reserve.worki.module.dto.CandidatePage;
import ru.iworking.personnel.reserve.worki.module.dto.CandidateSearchParamsDto;
import ru.iworking.personnel.reserve.worki.module.dto.GenderEnum;
import ru.iworking.personnel.reserve.worki.module.service.CandidateService;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class CandidatesPaneController implements Initializable {

    private static final Integer NUMBER_ELEMENTS_IN_PAGE = 50;
    private static final Integer NUMBER_PAGES_IN_INDICATOR = 10;
    private static final Integer START_INDEX_FOR_PAGINATION = 0;

    private static final String SYSTEM_NAME_PARAMETER_KEY_WORD = "workiModuleSearchKeyWord";
    private static final String SYSTEM_NAME_PARAMETER_ADDRESS = "workiModuleSearchAddress";
    private static final String SYSTEM_NAME_PARAMETER_ADDRESS_DISTANCE = "workiModuleSearchAddressDistance";
    private static final String SYSTEM_NAME_PARAMETER_EXPERIENCE_MONTHS = "workiModuleSearchExperienceMonths";

    private static final String TEMPLATE_NUMBER_KM_TEXT = "%s км.";
    private static final String TEMPLATE_EXPERIENCE_YEARS_TEXT = "%s %s";
    private static final String TEMPLATE_EXPERIENCE_YEARS_TEXT_DEFAULT = "не важен";

    private final ApplicationPropertiesProvider applicationPropertiesProvider;

    @Autowired @Lazy private CandidateService candidateService;
    @Autowired @Lazy private WorkiCandidateCellControllerFactory candidateCellControllerFactory;
    @Autowired @Lazy private CandidateViewController candidateViewController;

    @FXML private Pane parentPane;
    @FXML private Pagination candidatePagination;
    @FXML private Pane searchPane;
    @FXML private TextField keyWordTextField;
    @FXML private TextField addressTextField;
    @FXML private ComboBox<GenderEnum> genderComboBox;
    @FXML private TextField minAgeTextField;
    @FXML private TextField maxAgeTextField;
    @FXML private Slider distanceSlider;
    @FXML private Slider experienceSlider;
    @FXML private Label numberKmLabel;
    @FXML private Label numberExpLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minAgeTextField.setTextFormatter(new AgeFormatter());
        maxAgeTextField.setTextFormatter(new AgeFormatter());

        final GenderCellFactory cellFactory = new GenderCellFactory();
        genderComboBox.setButtonCell(cellFactory.call(null));
        genderComboBox.setCellFactory(cellFactory);
        ObservableList<GenderEnum> genderEnums = FXCollections.observableArrayList();
        genderEnums.add(GenderEnum.ANY);
        genderEnums.add(GenderEnum.MALE);
        genderEnums.add(GenderEnum.FEMALE);
        genderComboBox.setItems(genderEnums);

        distanceSlider.valueProperty().addListener((changed, oldValue, newValue) -> {
            String textLabel = String.format(TEMPLATE_NUMBER_KM_TEXT, newValue.intValue());
            numberKmLabel.setText(textLabel);
        });

        experienceSlider.valueProperty().addListener((changed, oldValue, newValue) -> {
            String textLabel = newValue.equals(0) ?
                    TEMPLATE_EXPERIENCE_YEARS_TEXT_DEFAULT :
                    String.format(TEMPLATE_EXPERIENCE_YEARS_TEXT, newValue.intValue(), TextUtil.nameForYears(newValue.intValue()));
            numberExpLabel.setText(textLabel);
        });
        this.loadSearchParams();
    }

    public void initData() {
        this.actionSearchParams(null);
    }

    private ListView createPage(Integer pageIndex) {
        ListView<CandidateDto> candidateListView = new ListView();
        candidateListView.getStyleClass().add("grey");
        candidateListView.setCellFactory(listView -> new WorkiCandidateCell(candidateCellControllerFactory));
        CandidatePage candidatePage = candidateService.findPageByNumber(pageIndex + 1);
        candidateListView.setItems(FXCollections.observableList(candidatePage.getCandidates()));
        candidateListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            candidateViewController.setData(newSelection);
            candidateViewController.show();
        });
        return candidateListView;
    }

    private Integer getPageCount(Integer totalElements, Integer pageSize) {
        Double pages = Math.ceil(Double.valueOf(totalElements) / pageSize);
        return pages.intValue();
    }

    public void isResizable(boolean isResizable) {
        if (isResizable) {
            searchPane.setMinWidth(0.00);
        } else {
            final double maxWidth1 = searchPane.getMaxWidth();
            searchPane.setMinWidth(maxWidth1);
        }
    }

    @FXML
    public void actionSearchParams(ActionEvent event) {
        CandidateSearchParamsDto searchParams = createSearchParams();
        this.saveSearchParams(searchParams);

        CandidatePage startPage = candidateService.findByParams(searchParams);
        Long totalElements = startPage.getMeta().getTotal();

        candidatePagination.setMaxPageIndicatorCount(NUMBER_PAGES_IN_INDICATOR);
        candidatePagination.setPageCount(getPageCount(totalElements.intValue(), NUMBER_ELEMENTS_IN_PAGE));
        candidatePagination.setCurrentPageIndex(START_INDEX_FOR_PAGINATION);
        candidatePagination.setPageFactory(this::createPage);
    }

    private CandidateSearchParamsDto createSearchParams() {
        CandidateSearchParamsDto searchParamsDto = new CandidateSearchParamsDto();
        searchParamsDto.setPage(START_INDEX_FOR_PAGINATION + 1);
        if (!Strings.isNullOrEmpty(keyWordTextField.getText())) searchParamsDto.setKeyWord(keyWordTextField.getText());
        if (!Strings.isNullOrEmpty(addressTextField.getText())) {
            searchParamsDto.setAddress(addressTextField.getText());
            Double distanceValue = distanceSlider.getValue();
            searchParamsDto.setDistance(distanceValue.intValue());
        }
        Double experienceYears = experienceSlider.getValue();
        if (experienceYears > 0) {
            Double experienceMonths = experienceYears * 12;
            searchParamsDto.setMonthExperience(experienceMonths.intValue());
        }
        GenderEnum gender = genderComboBox.getValue();
        if (!Objects.isNull(gender) && !gender.equals(GenderEnum.ANY)) {
            searchParamsDto.setGender(gender);
        }
        if (!Strings.isNullOrEmpty(minAgeTextField.getText())) {
            String minAgeStr = minAgeTextField.getText();
            searchParamsDto.setMinAge(Integer.valueOf(minAgeStr));
        }
        if (!Strings.isNullOrEmpty(maxAgeTextField.getText())) {
            String maxAgeStr = maxAgeTextField.getText();
            searchParamsDto.setMinAge(Integer.valueOf(maxAgeStr));
        }
        return searchParamsDto;
    }

    private void loadSearchParams() {
        String keyWord = applicationPropertiesProvider.getValueByName(SYSTEM_NAME_PARAMETER_KEY_WORD);
        if (!Strings.isNullOrEmpty(keyWord)) keyWordTextField.setText(keyWord);

        String searchAddress = applicationPropertiesProvider.getValueByName(SYSTEM_NAME_PARAMETER_ADDRESS);
        if (!Strings.isNullOrEmpty(searchAddress)) {
            addressTextField.setText(searchAddress);
            String searchAddressDistance = applicationPropertiesProvider.getValueByName(SYSTEM_NAME_PARAMETER_ADDRESS_DISTANCE);
            if (!Strings.isNullOrEmpty(searchAddress)) distanceSlider.setValue(Double.parseDouble(searchAddressDistance));
        }

        String searchExperienceMonths = applicationPropertiesProvider.getValueByName(SYSTEM_NAME_PARAMETER_EXPERIENCE_MONTHS);
        if (!Strings.isNullOrEmpty(searchExperienceMonths)) {
            Integer experienceMonths = Integer.parseInt(searchExperienceMonths);
            Double experienceYears = Math.ceil(experienceMonths / 12.00);
            experienceSlider.setValue(experienceYears);
        }
    }

    private void saveSearchParams(CandidateSearchParamsDto searchParams) {
        String keyWord = searchParams.getKeyWord();
        if (!Strings.isNullOrEmpty(keyWord)) {
            applicationPropertiesProvider.saveValue(SYSTEM_NAME_PARAMETER_KEY_WORD, keyWord);
        }

        String address = searchParams.getAddress();
        if (!Strings.isNullOrEmpty(address)) {
            applicationPropertiesProvider.saveValue(SYSTEM_NAME_PARAMETER_ADDRESS, searchParams.getAddress());
            if (Objects.nonNull(searchParams.getDistance())) {
                applicationPropertiesProvider.saveValue(SYSTEM_NAME_PARAMETER_ADDRESS_DISTANCE, searchParams.getDistance().toString());
            }
        }

        Integer experienceMonths = searchParams.getMonthExperience();
        if (Objects.nonNull(experienceMonths) && experienceMonths > 0) {
            applicationPropertiesProvider.saveValue(SYSTEM_NAME_PARAMETER_EXPERIENCE_MONTHS, experienceMonths.toString());
        }
    }

}
