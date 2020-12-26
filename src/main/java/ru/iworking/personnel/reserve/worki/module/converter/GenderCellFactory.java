package ru.iworking.personnel.reserve.worki.module.converter;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.iworking.personnel.reserve.worki.module.dto.GenderEnum;


public class GenderCellFactory implements Callback<ListView<GenderEnum>, ListCell<GenderEnum>> {

    @Override
    public ListCell<GenderEnum> call(ListView<GenderEnum> param) {
        return new ListCell<GenderEnum>() {
            @Override
            protected void updateItem(GenderEnum item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    try {
                        setText(item.getViewName());
                    } catch (Exception ex) {
                        setText("");
                    }

                }
            }
        };
    }

}
