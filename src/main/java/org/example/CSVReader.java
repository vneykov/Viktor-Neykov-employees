package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static final String ERROR_MESSAGE = "Error reading from input file";
    private static final String SEPARATOR = ",";
    private static final String NULL = "NULL";
    private static final int EMPLOYEE_ID_COLUMN = 0;
    private static final int PROJECT_ID_COLUMN = 1;
    private static final int DATE_FROM_COLUMN = 2;
    private static final int DATE_TO_COLUMN = 3;

    public static List<WorkRecord> readCSV(String fileName) {
        List<WorkRecord> workRecords = new ArrayList<>();
        LocalDate today = LocalDate.now();

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MESSAGE, e);
        }

        for (String line : lines) {
            String[] parts = line.split(SEPARATOR);
            int empId = Integer.parseInt(parts[EMPLOYEE_ID_COLUMN].replaceAll("\\D", "").trim());
            int projectId = Integer.parseInt(parts[PROJECT_ID_COLUMN].trim());
            LocalDate dateFrom = DateParser.parseDate(parts[DATE_FROM_COLUMN].trim());
            LocalDate dateTo = parts[DATE_TO_COLUMN].trim().equals(NULL) ?
                    today : DateParser.parseDate(parts[DATE_TO_COLUMN].trim());

            workRecords.add(new WorkRecord(empId, projectId, dateFrom, dateTo));
        }

        return workRecords;
    }
}
