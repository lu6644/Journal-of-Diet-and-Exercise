package Model.DataProcessing;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NutrientChartApplication extends JFrame {

    public NutrientChartApplication() {
        setTitle("Nutrient Intake Chart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NutrientPanel panel = new NutrientPanel();
        setContentPane(panel);
    }

    private class NutrientPanel extends JPanel {
        List<NutrientIntake> data;

        NutrientPanel() {
            NutrientIntakeModel model = new NutrientIntakeModel();
            Calendar cal = Calendar.getInstance();
            java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
            cal.add(Calendar.DAY_OF_YEAR, -30); // 获取过去30天的数据
            java.sql.Date thirtyDaysAgo = new java.sql.Date(cal.getTimeInMillis());
            data = model.getNutrientIntakeForPeriod(thirtyDaysAgo, today);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            int xScale = 50; 
            int yScale = 5; 
            int xOrigin = 50; 
            int yOrigin = getHeight() - 50; 

            // 绘制坐标轴
            g.drawLine(xOrigin, yOrigin, getWidth() - 50, yOrigin);
            g.drawLine(xOrigin, yOrigin, xOrigin, 50);
            
            drawAxisLabels(g, xOrigin, yOrigin, xScale, yScale);

            // 绘制数据点和连接线
            int prevX = xOrigin, prevY = yOrigin;
            for (int i = 0; i < data.size(); i++) {
                NutrientIntake intake = data.get(i);
                int x = xOrigin + i * xScale;
                int y = yOrigin - (int)intake.getProtein() * yScale; // 使用 getProtein 方法

                g.fillOval(x - 2, y - 2, 4, 4);

                if (i > 0) {
                    g.drawLine(prevX, prevY, x, y);
                }

                prevX = x;
                prevY = y;
            }
        }
        
        private void drawAxisLabels(Graphics g, int xOrigin, int yOrigin, int xScale, int yScale) {
            // 绘制x轴标签
            for (int i = 0; i < data.size(); i++) {
                String label = String.valueOf(i + 1); // 天数
                g.drawString(label, xOrigin + i * xScale - 5, yOrigin + 15);
            }

            // 绘制y轴标签
            double maxProtein = data.stream().mapToDouble(NutrientIntake::getProtein).max().orElse(0.0);
            for (int i = 0; i <= maxProtein; i += 10) {
                String label = String.valueOf(i);
                g.drawString(label, xOrigin - 30, yOrigin - i * yScale + 5);
            }
        }
    }

    

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            NutrientChartApplication frame = new NutrientChartApplication();
            frame.setVisible(true);
        });
    }
}
