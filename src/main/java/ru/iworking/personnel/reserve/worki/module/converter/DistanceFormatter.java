package ru.iworking.personnel.reserve.worki.module.converter;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class DistanceFormatter extends TextFormatter{

    private static final Pattern pattern = Pattern.compile("^\\d{0,3}");

    public DistanceFormatter() {
        super( (UnaryOperator<Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
    }

}
