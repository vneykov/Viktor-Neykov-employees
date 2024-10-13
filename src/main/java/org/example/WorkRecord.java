package org.example;

import java.time.LocalDate;

public record WorkRecord(int employeeId, int projectId, LocalDate dateFrom, LocalDate dateTo) {
}
