package View.DietExerciseDataUI;

import Controller.DataRequestHandler.DietsQueryController;
import View.ProfileUI.ProfileUIData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class DietJournalPage extends JFrame implements ActionListener {

    private List<Map<String,String>> dietsHistory;
    private ProfileUIData user;
    private JTable dietsHistoryTable;

    public DietJournalPage(ProfileUIData user) {
        this.user = user; // Store the profileId as an instance variable

        // Set up the JFrame
        setTitle("Diet History Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(800, 400);

        // Call the queryDietsHistory method to get nutrient data
        dietsHistory = DietsQueryController.getInstance().requestDietsHistory(user.getId());

        // Create a table model and set column names
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Diet No.", "Date", "Meal Type", "Calories Intake"});

        // Populate the table model with data
        for (Map<String,String> dietUIData : dietsHistory) {
            tableModel.addRow(new Object[]{dietUIData.get("id"), dietUIData.get("date"), dietUIData.get("mealType"), dietUIData.get("calories")});
        }

        // Create the JTable
        dietsHistoryTable = new JTable(tableModel);
        JTableHeader header = dietsHistoryTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(dietsHistoryTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        JButton viewNutrientsBreakdown = new JButton("View Nutrients Breakdown");
        viewNutrientsBreakdown.setActionCommand("viewBreakdown");
        viewNutrientsBreakdown.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(viewNutrientsBreakdown);

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comm = e.getActionCommand();
        if (comm.equals("viewBreakdown")){
            //Handles viewing nutrients breakdown
            int selectedRow = dietsHistoryTable.getSelectedRow();

            if (selectedRow != -1) { // Check if a row is selected
                DefaultTableModel model = (DefaultTableModel) dietsHistoryTable.getModel();
                String dietId = (String) model.getValueAt(selectedRow, 0);
                String date = (String) model.getValueAt(selectedRow, 1);
                String mealType = (String) model.getValueAt(selectedRow, 2);
                DietDetailPage.launch(dietId,date,mealType);
            } else {
                // No row is selected
                JOptionPane.showMessageDialog(this, "No row selected.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (comm.equals("back")){
            // Handle back button click
            this.dispose();
        }
    }

    public static void launch(ProfileUIData user){
        DietJournalPage djp = new DietJournalPage(user);
        djp.setSize(1280,720);
        djp.setVisible(true);
    }
}