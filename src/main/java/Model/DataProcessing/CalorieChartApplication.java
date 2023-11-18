package Model.DataProcessing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.List;

public class CalorieChartApplication extends JFrame {

    private JPanel contentPane;
    private JTextField daysInputField;
    private BarPanel barPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CalorieChartApplication frame = new CalorieChartApplication();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CalorieChartApplication() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 100, 800, 600);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        daysInputField = new JTextField();
        daysInputField.setColumns(10);

        JButton loadButton = new JButton("Load Data");
        loadButton.addActionListener(e -> loadData());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Days:"));
        inputPanel.add(daysInputField);
        inputPanel.add(loadButton);

        contentPane.add(inputPanel, BorderLayout.NORTH);

        barPanel = new BarPanel();
        contentPane.add(barPanel, BorderLayout.CENTER);
    }

    private void loadData() {
        try {
            int days = Integer.parseInt(daysInputField.getText());
            barPanel.setDays(days);
            barPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format");
        }
    }

    static class BarPanel extends JPanel {
        private int days;

        public void setDays(int days) {
            this.days = days;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (days > 0) {
                int userId = 1; // 示例用户ID
                Calendar calendar = Calendar.getInstance();
                Date endDate = new Date(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -days);
                Date startDate = new Date(calendar.getTimeInMillis());

                Map<String, Double> values = getAverageNutrientIntakes(userId, startDate, endDate);
                drawHorizontalBars(g, values);
            }
        }

        private void drawHorizontalBars(Graphics g, Map<String, Double> values) {
            int barHeight = 20;
            int y = 10;
            int maxBarLength = 400; // 最大条形图长度

            List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(values.entrySet());
            sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            sortedEntries = sortedEntries.subList(0, Math.min(10, sortedEntries.size()));

            for (Map.Entry<String, Double> entry : sortedEntries) {
                String nutrientName = entry.getKey();
                double value = entry.getValue();
                int barLength = (int) ((value / sortedEntries.get(0).getValue()) * maxBarLength);

                g.setColor(Color.BLUE);
                g.fillRect(230, y, barLength, barHeight);
                g.setColor(Color.BLACK);
                g.drawString(nutrientName, 10, y + barHeight / 2);
                g.drawString(String.format("%.2f", value), 230 + barLength + 5, y + barHeight / 2);

                y += barHeight + 10;
            }
        }

        private Map<String, Double> getAverageNutrientIntakes(int userId, Date startDate, Date endDate) {
            Map<String, Double> averageNutrientIntakes = new HashMap<>();
            String query = "SELECT NN.Nutrient_Name, AVG(NID.Value) AS AverageValue " +
                    "FROM nutrient_in_diet NID " +
                    "JOIN diet D ON NID.Diet_ID = D.id " +
                    "JOIN nutrient_name NN ON NID.Nutrient_ID = NN.Nutrient_ID " +
                    "WHERE D.account_id = ? AND D.date BETWEEN ? AND ? " +
                    "GROUP BY NN.Nutrient_Name";

            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, userId);
                stmt.setDate(2, startDate);
                stmt.setDate(3, endDate);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nutrientName = rs.getString("Nutrient_Name");
                    double averageValue = rs.getDouble("AverageValue");
                    averageNutrientIntakes.put(nutrientName, averageValue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return averageNutrientIntakes;
        }
    }
}
