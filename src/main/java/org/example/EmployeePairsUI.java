package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class EmployeePairsUI extends JFrame {
    private final DefaultTableModel tableModel;
    private static final String EMPLOYEE_PAIRS = "Employee Pairs";
    private static final String EMPLOYEE_ID = "Employee ID";
    private static final String PROJECT_ID = "Project ID";
    private static final String DAYS_WORKED = "Days Worked";
    private static final String LOAD_CSV = "Load CSV";
    private static final String SEPARATOR = ",";

    public EmployeePairsUI() {
        setTitle(EMPLOYEE_PAIRS);
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]
                {EMPLOYEE_ID, EMPLOYEE_ID, PROJECT_ID, DAYS_WORKED}, 0);

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton loadButton = new JButton(LOAD_CSV);
        loadButton.addActionListener(e -> driverMethod());
        add(loadButton, BorderLayout.SOUTH);
    }

    private void driverMethod() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            List<WorkRecord> workRecords = CSVReader.readCSV(selectedFile.getAbsolutePath());
            Map<String, Long> longestPair = CalculateLongestPeriod.calculatePairsWorkDuration(workRecords);
            displayPairsInTable(longestPair);
        }
    }

    private void displayPairsInTable(Map<String, Long> longestPair) {
        tableModel.setRowCount(0);

        for (var entry : longestPair.entrySet()) {
            String[] empPair = entry.getKey().split(SEPARATOR);
            tableModel.addRow(new Object[]{
                    empPair[0],
                    empPair[1],
                    empPair[2],
                    entry.getValue()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeePairsUI ui = new EmployeePairsUI();
            ui.setVisible(true);
        });
    }
}