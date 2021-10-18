package ru.iworking.personnel.reserve.worki.module.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.iworking.personnel.reserve.ApplicationContextProvider;
import ru.iworking.personnel.reserve.MainTabPaneProvider;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class WorkiModuleConfiguration {

    @Value("${worki.api-url:https://api.iconjob.co}")
    @Getter
    private String apiUrl;

    @Value("${worki.title-text:worki.ru}")
    @Getter
    private String titleText;

    @Value("${worki.default-page-size:5}")
    @Getter
    private Integer defaultPageSize;

    @Value("${worki.default-search-city:Москва}")
    @Getter
    private String defaultSearchCity;

    private final ApplicationContextProvider applicationContextProvider;
    private final MainTabPaneProvider mainTabPaneProvider;

    @PostConstruct
    public void init() {
        mainTabPaneProvider.addTab(createTab());
    }

    private Tab createTab() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/worki-module/WorkiTab.fxml"));
        loader.setControllerFactory(applicationContextProvider.getApplicationContext()::getBean);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tab tab = new Tab();
        tab.setContent(parent);
        tab.setText(titleText);
        return tab;
    }

}
