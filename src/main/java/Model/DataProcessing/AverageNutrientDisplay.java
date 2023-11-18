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
        setTitle("Nutrient Intake Chart");
        setSize(1000, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NutrientPieChartPanel panel = new NutrientPieChartPanel();
        setContentPane(panel);
    }

    private class NutrientPieChartPanel extends JPanel {
        Map<String, Double> nutrientAverages;
        Map<String, Color> nutrientColors = new HashMap<>();

        NutrientPieChartPanel() {
            nutrientAverages = getNutrientAverages();
            generateColors();
        }

        private void generateColors() {
            Random rand = new Random();
            nutrientAverages.forEach((key, value) -> nutrientColors.put(key, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawPieChart(g, nutrientAverages);
            drawLegend(g, nutrientAverages);
        }

        private void drawPieChart(Graphics g, Map<String, Double> nutrientAverages) {
            int x = 50; 
            int y = 50; 
            int width = 400; 
            int height = 400; 
            double total = nutrientAverages.values().stream().mapToDouble(Double::doubleValue).sum();
            int startAngle = 0;

            for (Map.Entry<String, Double> entry : nutrientAverages.entrySet()) {
                String nutrient = entry.getKey();
                double value = entry.getValue();
                int angle = (int) (value / total * 360);
                g.setColor(nutrientColors.get(nutrient));
                g.fillArc(x, y, width, height, startAngle, angle);

                
                double midAngle = Math.toRadians(startAngle + angle / 2.0);
                int labelX = x + width / 2 + (int) (width / 2.5 * Math.cos(midAngle));
                int labelY = y + height / 2 + (int) (height / 2.5 * Math.sin(midAngle));
                g.setColor(Color.BLACK);
                g.drawString(String.format("%.1f%%", (value / total * 100)), labelX, labelY);

                startAngle += angle;
            }
        }

        private void drawLegend(Graphics g, Map<String, Double> nutrientAverages) {
            int legendX = 500; 
            int legendY = 50; 
            int legendWidth = 150; 
            int legendHeight = 20; 
            double total = nutrientAverages.values().stream().mapToDouble(Double::doubleValue).sum();

            for (Map.Entry<String, Double> entry : nutrientAverages.entrySet()) {
                String nutrient = entry.getKey();
                double value = entry.getValue();

                
                g.setColor(nutrientColors.get(nutrient));
                g.fillRect(legendX, legendY, legendWidth, legendHeight);

                
                g.setColor(Color.BLACK);
                g.drawString(nutrient + " (" + String.format("%.1f%%", (value / total * 100)) + ")", legendX + legendWidth + 5, legendY + 15);

                legendY += legendHeight + 5;
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
            while (rs.next()) {
                String nutrientName = rs.getString("Nutrient_Name");
                double averageValue = rs.getDouble("AverageValue");
                averages.put(nutrientName, averageValue);
                total += averageValue;
            }

            
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

        return averages;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            AverageNutrientDisplay frame = new AverageNutrientDisplay();
            frame.setVisible(true);
        });
    }
}
