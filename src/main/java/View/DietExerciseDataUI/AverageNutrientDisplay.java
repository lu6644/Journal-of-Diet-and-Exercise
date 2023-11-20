package View.DietExerciseDataUI;

import javax.swing.*;

import Model.DatabaseInteraction.DatabaseConnector;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Date;

public class AverageNutrientDisplay extends JFrame {

    private int accountId;

    public AverageNutrientDisplay(int accountId) {
        this.accountId = accountId;

        setTitle("Nutrient Intake and Notification");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NutrientPieChartPanel pieChartPanel = new NutrientPieChartPanel();
        NutrientNotificationPanel notificationPanel = new NutrientNotificationPanel();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pieChartPanel, BorderLayout.CENTER);
        getContentPane().add(notificationPanel, BorderLayout.SOUTH);
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
            int x = 50, y = 50, width = 400, height = 400, startAngle = 0;
            double total = nutrientAverages.values().stream().mapToDouble(Double::doubleValue).sum();
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
                //g.drawString(String.format("%.1f%%", (value / total * 100)), labelX, labelY);
                startAngle += angle;
            }
        }

        private void drawLegend(Graphics g, Map<String, Double> nutrientAverages) {
            int legendX = 500, legendY = 50, legendWidth = 150, legendHeight = 20;
            double total = nutrientAverages.values().stream().mapToDouble(Double::doubleValue).sum();
            for (Map.Entry<String, Double> entry : nutrientAverages.entrySet()) {
                String nutrient = entry.getKey();
                double value = entry.getValue();
                g.setColor(nutrientColors.get(nutrient));
                g.fillRect(legendX, legendY, legendWidth, legendHeight);
                g.setColor(Color.BLACK);
                String unit = getUnitForNutrient(nutrient);
                g.drawString(nutrient + " (" + String.format("%.1f%%", (value / total * 100)) + " " + unit + ")", legendX + legendWidth + 5, legendY + 15);
                legendY += legendHeight + 5;
            }
        }
        
        private String getUnitForNutrient(String nutrient) {
            String unit = "";
            String query = "SELECT Nutrient_Unit FROM nutrient_name WHERE Nutrient_Name = ?";
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nutrient);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    unit = rs.getString("Nutrient_Unit");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return unit;
        }


        private Map<String, Double> getNutrientAverages() {
            Map<String, Double> averages = new HashMap<>();
            String query = "SELECT NN.Nutrient_Name, AVG(NID.Value) AS AverageValue " +
                           "FROM nutrient_in_diet NID " +
                           "JOIN diet D ON NID.Diet_ID = D.id " +
                           "JOIN nutrient_name NN ON NID.Nutrient_ID = NN.Nutrient_ID " +
                           "WHERE D.date BETWEEN ? AND ? AND NN.Nutrient_Unit IN ('g', 'mg') " + // 仅选择以克或毫克为单位的物质
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
                while (rs.next()) {
                    String nutrientName = rs.getString("Nutrient_Name");
                    double averageValue = rs.getDouble("AverageValue");
                    averages.put(nutrientName, averageValue);
                }
                final double threshold = 0.03 * averages.values().stream().mapToDouble(Double::doubleValue).sum();
                averages.entrySet().removeIf(e -> e.getValue() < threshold);
                averages.put("Others", averages.values().stream().filter(v -> v < threshold).mapToDouble(Double::doubleValue).sum());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return averages;
        }
    }

    private class NutrientNotificationPanel extends JPanel {
        double userWeight;
        double actualProtein;
        double actualCarbs;

        NutrientNotificationPanel() {
            setPreferredSize(new Dimension(1200, 200));
            fetchDataFromDatabase();
        }

        private void fetchDataFromDatabase() {
            userWeight = getUserWeight();
            actualProtein = getNutrientIntake("Protein");
            actualCarbs = getNutrientIntake("CARBOHYDRATE, TOTAL (BY DIFFERENCE)");
        }

        private double getUserWeight() {
            String sql = "SELECT weight FROM account WHERE account_id = ?";
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, accountId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("weight");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        private double getNutrientIntake(String nutrientName) {
            String sql = "SELECT AVG(NID.Value) AS AverageValue " +
                         "FROM nutrient_in_diet NID " +
                         "JOIN diet D ON NID.Diet_ID = D.id " +
                         "JOIN nutrient_name NN ON NID.Nutrient_ID = NN.Nutrient_ID " +
                         "WHERE NN.Nutrient_Name = ? AND D.account_id = ?";
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nutrientName);
                pstmt.setInt(2, accountId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("AverageValue");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            displayNotification(g);
        }

        private void displayNotification(Graphics g) {
            Map<String, Double> recommendedIntake = calculateRecommendedIntake(userWeight);
            g.setColor(Color.BLACK);
            g.drawString("Recommended Daily Intake", 20, 30);

            String actualProteinStr = String.format("%.2f", actualProtein);
            String recommendedProteinStr = String.format("%.2f", recommendedIntake.get("Protein"));
            g.drawString("Protein: " + actualProteinStr + "g (Recommended: " + recommendedProteinStr + "g)", 20, 60);

            String actualCarbsStr = String.format("%.2f", actualCarbs);
            String recommendedCarbsStr = String.format("%.2f", recommendedIntake.get("Carbohydrates"));
            g.drawString("Carbs: " + actualCarbsStr + "g (Recommended: " + recommendedCarbsStr + "g)", 20, 90);
        }

        private Map<String, Double> calculateRecommendedIntake(double weight) {
            Map<String, Double> recommendedIntake = new HashMap<>();
            recommendedIntake.put("Protein", weight * 0.8);
            recommendedIntake.put("Carbohydrates", 275.0);
            return recommendedIntake;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            int accountId = 1;
            AverageNutrientDisplay frame = new AverageNutrientDisplay(accountId);
            frame.setVisible(true);
        });
    }
}
