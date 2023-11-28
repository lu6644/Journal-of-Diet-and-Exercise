package View.DataVisulizationUI;

import java.awt.*;
import javax.swing.*;

import Controller.DataRequestHandler.ProfilesQueryController;
import Model.DataProcessing.NutrientIntake;
import Model.DatabaseInteraction.NutrientIntakeModel;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.MainUI.NavigateUI;
import View.ProfileUI.ProfileUIData;

import java.util.Calendar;
import java.util.List;

/**
 * Application for displaying a chart of nutrient intake.
 */
public class NutrientChartDisplay extends JFrame {

    /**
     * Constructor to initialize the nutrient chart application.
     */
    private ProfileUIData user;
    public NutrientChartDisplay(ProfileUIData user) {
        this.user = user;
        setTitle("Nutrient Intake Chart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NutrientPanel panel = new NutrientPanel();
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(panel,BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            NavigateUI.launch(user);
        });
        c.add(backButton,BorderLayout.SOUTH);
    }

    /**
     * Inner class representing the panel where the nutrient chart is drawn.
     */
    private class NutrientPanel extends JPanel {
        List<NutrientIntake> data;

        /**
         * Constructor to initialize the nutrient panel.
         */
        NutrientPanel() {
            NutrientIntakeModel model = new NutrientIntakeModel();
            Calendar cal = Calendar.getInstance();
            java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
            cal.add(Calendar.DAY_OF_YEAR, -30); // Retrieve data from the last 30 days.
            java.sql.Date thirtyDaysAgo = new java.sql.Date(cal.getTimeInMillis());
            data = model.getNutrientIntakeForPeriod(thirtyDaysAgo, today);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Scales and origins for the chart
            int xScale = 50;
            int yScale = 5;
            int xOrigin = 50;
            int yOrigin = getHeight() - 50;

            // Drawing axes
            g.drawLine(xOrigin, yOrigin, getWidth() - 50, yOrigin);
            g.drawLine(xOrigin, yOrigin, xOrigin, 50);
            
            // Drawing axis labels
            drawAxisLabels(g, xOrigin, yOrigin, xScale, yScale);

            // Plotting data points and connecting lines
            int prevX = xOrigin, prevY = yOrigin;
            for (int i = 0; i < data.size(); i++) {
                NutrientIntake intake = data.get(i);
                int x = xOrigin + i * xScale;
                int y = yOrigin - (int)intake.getProtein() * yScale; // Plotting based on protein intake

                g.fillOval(x - 2, y - 2, 4, 4);

                if (i > 0) {
                    g.drawLine(prevX, prevY, x, y);
                }

                prevX = x;
                prevY = y;
            }
        }
        
        private void drawAxisLabels(Graphics g, int xOrigin, int yOrigin, int xScale, int yScale) {
        	// Draw x-axis labels
            for (int i = 0; i < data.size(); i++) {
                String label = String.valueOf(i + 1);
                g.drawString(label, xOrigin + i * xScale - 5, yOrigin + 15);
            }
         // Add "Days" label at the end of the x-axis
            g.drawString("Days", getWidth() - 60, yOrigin + 20);

         // Draw y-axis labels
            double maxProtein = data.stream().mapToDouble(NutrientIntake::getProtein).max().orElse(0.0);
            for (int i = 0; i <= maxProtein; i += 10) {
                String label = String.valueOf(i);
                g.drawString(label, xOrigin - 30, yOrigin - i * yScale + 5);
            }
         // Add "Calories Intake (kcal)" label at the top of the y-axis
            g.drawString("Calories Intake (kcal)", xOrigin - 60, 45);
        }
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ProfileUIData user = ProfilesQueryController.getInstance().getProfile(1);
            NutrientChartDisplay.launch(user);
        });
    }

    public static void launch(ProfileUIData user){
        NutrientChartDisplay frame = new NutrientChartDisplay(user);
        frame.setVisible(true);
    }
}
