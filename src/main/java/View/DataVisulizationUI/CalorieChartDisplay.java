package View.DataVisulizationUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controller.DataRequestHandler.ProfilesQueryController;
import Model.DatabaseInteraction.DatabaseConnector;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.MainUI.NavigateUI;
import View.ProfileUI.ProfileUIData;

import java.awt.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

public class CalorieChartDisplay extends JFrame {

    private JPanel contentPane;
    private JTextField daysInputField;
    private BarPanel barPanel;
    private ProfileUIData user;

    // Main method to run the application
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ProfileUIData user = ProfilesQueryController.getInstance().getProfile(1);
                CalorieChartDisplay.launch(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Constructor to set up the application frame
    public CalorieChartDisplay(ProfileUIData user) {
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setBounds(600, 100, 800, 600); // Set the size and position of the frame
        contentPane = new JPanel(new BorderLayout()); // Create a content pane with BorderLayout
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Set the border for the pane
        setContentPane(contentPane); // Set the content pane for the frame

        daysInputField = new JTextField(); // Create a text field for input
        daysInputField.setColumns(10); // Set the column size

        JButton loadButton = new JButton("Load Data"); // Create a button to load data
        loadButton.addActionListener(e -> loadData()); // Add an action listener to the button

        JPanel inputPanel = new JPanel(); // Create a panel for input
        inputPanel.add(new JLabel("Enter Days:")); // Add a label
        inputPanel.add(daysInputField); // Add the text field
        inputPanel.add(loadButton); // Add the button

        contentPane.add(inputPanel, BorderLayout.NORTH); // Add the input panel to the content pane

        barPanel = new BarPanel(); // Create a panel for the bar chart
        contentPane.add(barPanel, BorderLayout.CENTER); // Add the bar panel to the content pane

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            NavigateUI.launch(user);
        });
        contentPane.add(backButton,BorderLayout.SOUTH);
    }

    // Method to load data based on the input days
    private void loadData() {
        try {
            int days = Integer.parseInt(daysInputField.getText()); // Parse the input days
            barPanel.setDays(days); // Set the days in the bar panel
            barPanel.repaint(); // Repaint the bar panel to update the chart
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format"); // Show error message
        }
    }

    // Inner class to create a custom panel for displaying the bar chart
    static class BarPanel extends JPanel {
        private int days; // Number of days for the chart

        // Setter method for days
        public void setDays(int days) {
            this.days = days;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Only draw the chart if days are greater than 0
            if (days > 0) {
                int userId = 1; // Example user ID
                Calendar calendar = Calendar.getInstance();
                Date endDate = new Date(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -days);
                Date startDate = new Date(calendar.getTimeInMillis());

                // Get the average nutrient intakes
                Map<String, Double> values = getAverageNutrientIntakes(userId, startDate, endDate);
                drawHorizontalBars(g, values); // Draw the horizontal bars
            }
        }

        // Method to draw horizontal bars representing average nutrient intakes
        private void drawHorizontalBars(Graphics g, Map<String, Double> values) {
            int barHeight = 20; // Height of each bar
            int y = 10; // Initial Y position
            int maxBarLength = 400; // Maximum length of the bar

            // Sort entries by value in descending order and take the top 10
            List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(values.entrySet());
            sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            sortedEntries = sortedEntries.subList(0, Math.min(10, sortedEntries.size()));

            // Draw each bar
            for (Map.Entry<String, Double> entry : sortedEntries) {
                String nutrientName = entry.getKey();
                double value = entry.getValue();
                int barLength = (int) ((value / sortedEntries.get(0).getValue()) * maxBarLength);

                g.setColor(Color.BLUE); // Set the color for the bar
                g.fillRect(230, y, barLength, barHeight); // Draw the bar
                g.setColor(Color.BLACK); // Set the color for the text
                g.drawString(nutrientName, 10, y + barHeight / 2); // Draw the nutrient name
                g.drawString(String.format("%.2f", value), 230 + barLength + 5, y + barHeight / 2); // Draw the value

                y += barHeight + 10; // Increment the Y position for the next bar
            }
        }

        // Method to get average nutrient intakes from the database
        private Map<String, Double> getAverageNutrientIntakes(int userId, Date startDate, Date endDate) {
            Map<String, Double> averageNutrientIntakes = new HashMap<>();
            String query = "SELECT NN.Nutrient_Name, AVG(NID.Value) AS AverageValue " +
                    "FROM nutrient_in_diet NID " +
                    "JOIN diet D ON NID.Diet_ID = D.id " +
                    "JOIN nutrient_name NN ON NID.Nutrient_ID = NN.Nutrient_ID " +
                    "WHERE D.account_id = ? AND D.date BETWEEN ? AND ? " +
                    "GROUP BY NN.Nutrient_Name";

            // Execute the query and collect results
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, userId); // Set the user ID parameter
                stmt.setDate(2, startDate); // Set the start date parameter
                stmt.setDate(3, endDate); // Set the end date parameter

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nutrientName = rs.getString("Nutrient_Name");
                    double averageValue = rs.getDouble("AverageValue");
                    averageNutrientIntakes.put(nutrientName, averageValue); // Store the average values
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return averageNutrientIntakes; // Return the map of average nutrient intakes
        }
    }

    public static void launch(ProfileUIData user){
        CalorieChartDisplay frame = new CalorieChartDisplay(user);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
