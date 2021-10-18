package ru.iworking.personnel.reserve.worki.module.dto;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import ru.iworking.personnel.reserve.utils.TextUtil;
import ru.iworking.personnel.reserve.utils.TimeUtil;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {
    protected String title;
    protected String description;
    protected Integer duration;
    protected Boolean current;
    protected ProfessionDto profession;

    public String getPeriod() {
        if (Objects.isNull(duration)) duration = 0;

        Interval interval = new Interval(DateTime.now().minusMonths(duration), DateTime.now());

        Integer years = interval.toPeriod().getYears();
        Integer months = interval.toPeriod().getMonths();

        String period = "";
        if (years > 0) period += String.format("%s %s", years, TextUtil.nameForYears(years));
        if (months > 0) {
            if (!Strings.isNullOrEmpty(period)) period += ", ";
            period += String.format("%s %s", months, TextUtil.nameForMonths(months));
        }

        return period;
    }

}
