package ru.iworking.personnel.reserve.worki.module.converter;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NumberFormatter extends TextFormatter{

    private static final Pattern pattern = Pattern.compile("^\\d*");

    public NumberFormatter() {
        super( (UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
    }

}
