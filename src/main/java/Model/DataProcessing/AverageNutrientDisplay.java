package Model.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AverageNutrientDisplay extends JFrame {

    public AverageNutrientDisplay() {
        setTitle("Nutrient Intake Chart"); // Set the title of the window
        setSize(1000, 600); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

        NutrientPieChartPanel panel = new NutrientPieChartPanel(); // Create the pie chart panel
        setContentPane(panel); // Add the panel to the frame
    }

    private class NutrientPieChartPanel extends JPanel {
        Map<String, Double> nutrientAverages; // Stores average values of nutrients
        Map<String, Color> nutrientColors = new HashMap<>(); // Stores colors for each nutrient

        NutrientPieChartPanel() {
            nutrientAverages = getNutrientAverages(); // Get the average values
            generateColors(); // Generate random colors for the pie chart
        }

        private void generateColors() {
            Random rand = new Random();
            // Assign a random color for each nutrient
            nutrientAverages.forEach((key, value) -> nutrientColors.put(key, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawPieChart(g, nutrientAverages); // Draw the pie chart
            drawLegend(g, nutrientAverages); // Draw the legend for the chart
        }

        private void drawPieChart(Graphics g, Map<String, Double> nutrientAverages) {
            // Pie chart dimensions and initial angle
            int x = 50, y = 50, width = 400, height = 400, startAngle = 0;
            double total = nutrientAverages.values().stream().mapToDouble(Double::doubleValue).sum(); // Total of all nutrient values

            // Draw each slice of the pie chart
            for (Map.Entry<String, Double> entry : nutrientAverages.entrySet()) {
                String nutrient = entry.getKey();
                double value = entry.getValue();
                int angle = (int) (value / total * 360); // Calculate the angle for each slice
                g.setColor(nutrientColors.get(nutrient)); // Set color for the slice
                g.fillArc(x, y, width, height, startAngle, angle); // Draw the slice

                // Calculate the position for the label
                double midAngle = Math.toRadians(startAngle + angle / 2.0);
                int labelX = x + width / 2 + (int) (width / 2.5 * Math.cos(midAngle));
                int labelY = y + height / 2 + (int) (height / 2.5 * Math.sin(midAngle));
                g.setColor(Color.BLACK);
                g.drawString(String.format("%.1f%%", (value / total * 100)), labelX, labelY); // Draw the label

                startAngle += angle; // Update the start angle for the next slice
            }
        }

        private void drawLegend(Graphics g, Map<String, Double> nutrientAverages) {
            // Legend dimensions and initial position
            int legendX = 500, legendY = 50, legendWidth = 150, legendHeight = 20;
            double total = nutrientAverages.values().stream().mapToDouble(Double::doubleValue).sum(); // Total of all nutrient values

            // Draw each entry in the legend
            for (Map.Entry<String, Double> entry : nutrientAverages.entrySet()) {
                String nutrient = entry.getKey();
                double value = entry.getValue();

                g.setColor(nutrientColors.get(nutrient)); // Set color for the legend entry
                g.fillRect(legendX, legendY, legendWidth, legendHeight); // Draw the legend box

                g.setColor(Color.BLACK);
                // Draw the text for the legend entry
                g.drawString(nutrient + " (" + String.format("%.1f%%", (value / total * 100)) + ")", legendX + legendWidth + 5, legendY + 15);

                legendY += legendHeight + 5; // Update position for the next entry
            }
        }

    }

    private Map<String, Double> getNutrientAverages() {
        Map<String, Double> averages = new HashMap<>();
        String query = "SELECT NN.Nutrient_Name, AVG(NID.Value) AS AverageValue " +
                       "FROM nutrient_in_diet NID " +
                       "JOIN diet D ON NID.Diet_ID = D.id " +
                       "JOIN nutrient_name NN ON NID.Nutrient_ID = NN.Nutrient_ID " +
                       "WHERE D.date BETWEEN ? AND ? " +
                       "GROUP BY NN.Nutrient_Name";

        // Calculate date range for the query
        Calendar calendar = Calendar.getInstance();
        Date endDate = new Date(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date startDate = new Date(calendar.getTimeInMillis());

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);

            ResultSet rs = stmt.executeQuery();
            double total = 0;
            // Collect and calculate average values for each nutrient
            while (rs.next()) {
                String nutrientName = rs.getString("Nutrient_Name");
                double averageValue = rs.getDouble("AverageValue");
                averages.put(nutrientName, averageValue);
                total += averageValue;
            }

            // Filter out low values and aggregate them under "Others"
            final double threshold = 0.03 * total;
            double others = averages.entrySet().stream()
                .filter(e -> e.getValue() < threshold)
                .mapToDouble(Map.Entry::getValue)
                .sum();

            averages.entrySet().removeIf(e -> e.getValue() < threshold);
            if (others > 0) {
                averages.put("Others", others);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averages; // Return the calculated averages
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            AverageNutrientDisplay frame = new AverageNutrientDisplay();
            frame.setVisible(true); // Display the frame
        });
    }
}
