package View.FatLossPredictorUI;

import javax.swing.*;

import Model.DataProcessing.FatLossCalculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FatLossPredictorUI extends JFrame {

    private JTextField dateField;
    private JButton predictButton;
    private JLabel resultLabel;
    private int accountId; // Assume this is the account ID for which we are calculating

    public FatLossPredictorUI(int accountId) {
        this.accountId = accountId;
        createUI();
    }

    private void createUI() {
        setTitle("Fat Loss Predictor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        dateField = new JTextField(10);
        predictButton = new JButton("Predict");
        resultLabel = new JLabel("Enter a future date and click Predict");

        add(dateField);
        add(predictButton);
        add(resultLabel);

        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predictFatLoss();
            }
        });
    }

    private void predictFatLoss() {
        try {
            String dateString = dateField.getText();
            Date futureDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            FatLossCalculator calculator = new FatLossCalculator();
            double predictedLoss = calculator.calculateExpectedFatLoss(futureDate, accountId);
            String formattedResult = String.format("%.2f", predictedLoss);
            resultLabel.setText("Expected Fat Loss: " + formattedResult + " kg");
        } catch (Exception ex) {
            resultLabel.setText("Invalid date format or calculation error");
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            int accountId = 1; // Example account ID
            FatLossPredictorUI frame = new FatLossPredictorUI(accountId);
            frame.setVisible(true);
        });
    }
}