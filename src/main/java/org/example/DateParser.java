package org.example;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DateParser {
    private static final List<DateTimeFormatter> formatters = new ArrayList<>();

    private static final String UNSUPPORTED_DATE_FORMAT = "Unsupported date time format";
    private static final String UNABLE_TO_PARSE_DATE = "Unable to parse date: ";

    static {
        formatters.add(DateTimeFormatter.ofPattern("M/d/yyyy"));
        formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        formatters.add(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public static LocalDate parseDate(String dateStr) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr.trim(), formatter);
            } catch (DateTimeException e) {
                throw new DateTimeException(UNSUPPORTED_DATE_FORMAT, e);
            }
        }
        throw new DateTimeParseException(UNABLE_TO_PARSE_DATE + dateStr, dateStr, 0);
    }
}
