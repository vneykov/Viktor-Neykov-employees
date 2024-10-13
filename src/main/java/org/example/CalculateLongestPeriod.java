package org.example;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class CalculateLongestPeriod {

    public static Map<String, Long> calculatePairsWorkDuration(List<WorkRecord> WorkRecords) {
        Map<String, List<WorkRecord>> projectsMap = new HashMap<>();

        // Group employees by project
        for (WorkRecord workRecord : WorkRecords) {
            projectsMap.putIfAbsent(workRecord.projectId() + "", new ArrayList<>());
            projectsMap.get(workRecord.projectId() + "").add(workRecord);
        }

        Map<String, Long> pairsWorkedTogether = new HashMap<>();

        for (List<WorkRecord> projectEmployees : projectsMap.values()) {
            // Sort employees by start date (DateFrom)
            projectEmployees.sort(Comparator.comparing(WorkRecord::dateFrom));

            // Compare overlapping periods in O(n) for each project
            for (int i = 0; i < projectEmployees.size(); i++) {
                WorkRecord emp1 = projectEmployees.get(i);
                for (int j = i + 1; j < projectEmployees.size(); j++) {
                    WorkRecord emp2 = projectEmployees.get(j);

                    if (emp2.dateFrom().isAfter(emp1.dateTo())) {
                        break;
                    }

                    LocalDate overlapStart = emp1.dateFrom().isAfter(emp2.dateFrom()) ? emp1.dateFrom() : emp2.dateFrom();
                    LocalDate overlapEnd = emp1.dateTo().isBefore(emp2.dateTo()) ? emp1.dateTo() : emp2.dateTo();

                    if (!overlapStart.isAfter(overlapEnd)) {
                        long daysTogether = Duration.between(overlapStart.atStartOfDay(), overlapEnd.atStartOfDay()).toDays();
                        String pairKey = emp1.employeeId() + "," + emp2.employeeId() + "," + emp1.projectId();

                        pairsWorkedTogether.put(pairKey, pairsWorkedTogether.getOrDefault(pairKey, 0L) + daysTogether);
                    }
                }
            }
        }

        return pairsWorkedTogether;
    }
}
