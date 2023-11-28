package View.FatLossPredictorUI;

import javax.swing.*;

import Controller.DataRequestHandler.ProfilesQueryController;
import Model.DataProcessing.FatLossCalculator;
import View.MainUI.NavigateUI;
import View.ProfileUI.ProfileUIData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FatLossPredictorUI extends JFrame {

    private JTextField dateField;
    private JButton predictButton;
    private JLabel resultLabel;
    private ProfileUIData user; // Assume this is the account for which we are calculating

    private JButton backButton;

    public FatLossPredictorUI(ProfileUIData user) {
        this.user = user;
        createUI();
    }

    private void createUI() {
        setTitle("Fat Loss Predictor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        dateField = new JTextField(10);
        predictButton = new JButton("Predict");
        resultLabel = new JLabel("Enter a future date and click Predict");

        JPanel topRow = new JPanel(new FlowLayout());
        topRow.add(dateField);
        topRow.add(predictButton);
        topRow.add(resultLabel);
        add(topRow,BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            NavigateUI.launch(user);
        });

        add(backButton,BorderLayout.SOUTH);

        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predictFatLoss();
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void predictFatLoss() {
        try {
            String dateString = dateField.getText();
            Date futureDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            FatLossCalculator calculator = new FatLossCalculator();
            double predictedLoss = calculator.calculateExpectedFatLoss(futureDate, user.getId());
            String formattedResult = String.format("%.2f", predictedLoss);
            resultLabel.setText("Expected Fat Loss: " + formattedResult + " kg");
        } catch (Exception ex) {
            resultLabel.setText("Invalid date format or calculation error");
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ProfileUIData user = ProfilesQueryController.getInstance().getProfile(1);; // Example account ID 1
            FatLossPredictorUI frame = new FatLossPredictorUI(user);
            frame.setVisible(true);
        });
    }
}