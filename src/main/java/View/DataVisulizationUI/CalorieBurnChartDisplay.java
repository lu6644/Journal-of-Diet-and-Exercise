package View.DataVisulizationUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

import Controller.DataRequestHandler.ProfilesQueryController;
import Model.DatabaseInteraction.DatabaseConnector;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.ProfileUI.ProfileUIData;

public class CalorieBurnChartDisplay extends JPanel {
    private List<DataPoint> dataPoints = new ArrayList<>();
    private ProfileUIData user;

    public CalorieBurnChartDisplay(ProfileUIData user) {
        // Load data from the database when the application starts
        loadDataFromDatabase();
        this.user = user;
    }

    private void loadDataFromDatabase() {
        // Use a map to store the sum of calories burned by date
        Map<Date, Double> calorieSumByDate = new HashMap<>();

        try (Connection conn = DatabaseConnector.getConnection()) {
            // SQL query to select exercise date and calories burned
            String sql = "SELECT el.exercise_date, ecb.calories_burnt " +
                         "FROM exercise_log el " +
                         "JOIN exercise_calories_burnt ecb ON el.exercise_id = ecb.exercise_id " +
                         "ORDER BY el.exercise_date";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    // Collecting data from the result set
                    Date date = rs.getDate("exercise_date");
                    double caloriesBurnt = rs.getDouble("calories_burnt");
                    // Summing calories by date
                    calorieSumByDate.merge(date, caloriesBurnt, Double::sum);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Convert the map to a sorted list of DataPoints
        dataPoints = calorieSumByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new DataPoint(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the graph
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        if (dataPoints.isEmpty()) {
            return;
        }

        // Set basic parameters for the graph
        int padding = 50;
        int labelPadding = 25;
        int width = getWidth() - (2 * padding);
        int height = getHeight() - (2 * padding);

        // Create axes and set up graphics
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the x and y axes
        g2.drawLine(padding + labelPadding, height + padding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, height + padding, width + padding, height + padding);

        // Find the maximum calorie value for scaling
        double maxCalories = dataPoints.stream().mapToDouble(p -> p.caloriesBurnt).max().orElse(1);

        // Set date format for x-axis labels
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");

        // Draw data points and lines connecting them
        for (int i = 0; i < dataPoints.size(); i++) {
            int x1 = i * width / (dataPoints.size() - 1) + padding + labelPadding;
            int y1 = (int) ((maxCalories - dataPoints.get(i).caloriesBurnt) * height / maxCalories + padding);

            // Draw data points
            g2.setColor(Color.BLUE);
            g2.fillOval(x1 - 2, y1 - 2, 4, 4);

            // Draw labels for each data point
            String dateLabel = dateFormat.format(dataPoints.get(i).date);
            g2.drawString(dateLabel, x1 - 15, height + padding + 20);
            String caloriesLabel = String.format("%.2f cal", dataPoints.get(i).caloriesBurnt);
            g2.drawString(caloriesLabel, x1 - 15, y1 - 5);

            // Draw lines connecting data points
            if (i < dataPoints.size() - 1) {
                int x2 = (i + 1) * width / (dataPoints.size() - 1) + padding + labelPadding;
                int y2 = (int) ((maxCalories - dataPoints.get(i + 1).caloriesBurnt) * height / maxCalories + padding);

                g2.setColor(Color.RED);
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

    // Inner class to represent data points
    private static class DataPoint {
        Date date;
        double caloriesBurnt;

        public DataPoint(Date date, double caloriesBurnt) {
            this.date = date;
            this.caloriesBurnt = caloriesBurnt;
        }
    }

    public static void launch(ProfileUIData user){
        JFrame frame = new JFrame("Calorie Burn Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CalorieBurnChartDisplay(user));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Setting up the frame for the application
        JFrame frame = new JFrame("Calorie Burn Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ProfileUIData user = ProfilesQueryController.getInstance().getProfile(1);
        frame.add(new CalorieBurnChartDisplay(user));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
