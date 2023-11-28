package View.DataVisulizationUI;

import Controller.DataRequestHandler.FoodsQueryController;
import View.MainUI.NavigateUI;
import View.ProfileUI.ProfileUIData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class CFGComparisionPage extends JFrame {


    public CFGComparisionPage(ProfileUIData user) {

        setTitle("Compare Your Food Intake with CFG Recommendations");

        FoodsQueryController foodsQueryController = FoodsQueryController.getInstance();

        // Create the dataset of actual average plate
        DefaultPieDataset actualFoodPortions =
                createDataset(foodsQueryController.getActualFoodPortions(user.getId()));

        // Create the first chart
        JFreeChart chart1 = createPieChart("Actual Average Plate", actualFoodPortions);

        // Create the dataset of recommended CFG servings for each food group
        DefaultPieDataset recommendedFoodPortions =
                createDataset(foodsQueryController.getRecommendedFoodPortions(user.getId()));

        // Create the second chart
        JFreeChart chart2 = createPieChart("CFG's Recommended Plate", recommendedFoodPortions);

        // Create a panel to hold the charts
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new ChartPanel(chart1));
        panel.add(new ChartPanel(chart2));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,40,10));

        // Add the panel to the frame
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(panel);

        //back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.addActionListener(e -> {
            this.dispose();
            NavigateUI.launch(user);
        });
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        c.add(backButton, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private DefaultPieDataset createDataset(Map<String,Double> foodCategoriesPortions) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String,Double> entry:foodCategoriesPortions.entrySet()){
            dataset.setValue(entry.getKey(),entry.getValue());
        }
        return dataset;
    }

    private JFreeChart createPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataset,
                true,
                true,
                false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}({2})"));
        plot.setLabelFont(new Font("SansSerif", Font.BOLD, 12));

        return chart;
    }
    public static void launch(ProfileUIData user) {
        SwingUtilities.invokeLater(() -> {
            CFGComparisionPage cfgPage = new CFGComparisionPage(user);
            cfgPage.setSize(1280, 720);
            cfgPage.setLocationRelativeTo(null);
            cfgPage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            cfgPage.setVisible(true);
        });
    }
}

