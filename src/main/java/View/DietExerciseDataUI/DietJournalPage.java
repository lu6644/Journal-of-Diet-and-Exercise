package View.DietExerciseDataUI;

import Controller.DataRequestHandler.DietsQueryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class DietJournalPage extends JFrame {

    private List<Map<String,String>> dietsHistory;
    private int profileId = 1; // Private attribute

    public DietJournalPage(int profileId) {
        this.profileId = profileId; // Store the profileId as an instance variable

        // Set up the JFrame
        setTitle("Diet History Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // Call the queryDietsHistory method to get nutrient data
        dietsHistory = DietsQueryController.getInstance().requestDietsHistory(profileId);

        // Create a table model and set column names
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Diet No.", "Date", "Meal Type", "Calories Intake"});

        // Populate the table model with data
        for (Map<String,String> dietUIData : dietsHistory) {
            tableModel.addRow(new Object[]{dietUIData.get("id"), dietUIData.get("date"), dietUIData.get("mealType"), dietUIData.get("calories")});
        }

        // Create the JTable
        JTable nutrientTable = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(nutrientTable);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("View Nutrients Breakdown");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public static void launch(int profileId){
        DietJournalPage djp = new DietJournalPage(profileId);
        djp.setSize(1280,720);
        djp.setVisible(true);
    }
}