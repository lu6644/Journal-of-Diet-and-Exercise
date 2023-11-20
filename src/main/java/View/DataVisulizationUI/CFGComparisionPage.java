package View.DataVisulizationUI;

import Controller.DataRequestHandler.FoodsQueryController;
import Controller.DataRequestHandler.ProfilesQueryController;
import View.MainUI.NavigateUI;
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

    int pid;
    public CFGComparisionPage(int profileId) {
        this.pid = profileId;
        setTitle("Compare Your Food Intake with CFG Recommendations");

        FoodsQueryController foodsQueryController = FoodsQueryController.getInstance();

        // Create the dataset of actual average plate
        DefaultPieDataset actualFoodPortions =
                createDataset(foodsQueryController.getActualFoodPortions(profileId));

        // Create the first chart
        JFreeChart chart1 = createPieChart("Actual Average Plate", actualFoodPortions);

        // Create the dataset of recommended CFG servings for each food group
        DefaultPieDataset recommendedFoodPortions =
                createDataset(foodsQueryController.getRecommendedFoodPortions(profileId));

        // Create the second chart
        JFreeChart chart2 = createPieChart("CFG's Recommended Plate", recommendedFoodPortions);

        // Create a panel to hold the charts
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new ChartPanel(chart1));
        panel.add(new ChartPanel(chart2));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAction();
            }
        });

        panel.add(backButton);
        // Add the panel to the frame
        setContentPane(panel);
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

    public void backAction(){
        this.dispose();

        NavigateUI.launch(ProfilesQueryController.getInstance().getProfile(pid));
    }

    public static void launch(int profileId) {
        SwingUtilities.invokeLater(() -> {
            CFGComparisionPage example = new CFGComparisionPage(profileId);
            example.setSize(1280, 720);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}

