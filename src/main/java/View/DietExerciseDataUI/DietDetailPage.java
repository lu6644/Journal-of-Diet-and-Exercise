package View.DietExerciseDataUI;

import Controller.DataRequestHandler.DietsQueryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class DietDetailPage extends JFrame implements ActionListener {
    private JLabel title;
    private JLabel dietNumberLabel;
    private JLabel dateLabel;
    private JLabel mealTypeLabel;
    private JTable nutrientTable;
    private JButton backButton;

    public DietDetailPage(String dietId, String date, String mealType) {
        setTitle("Nutrients Information");
        //setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        JPanel labelsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Set weight to make the components evenly distributed
        gbc.insets = new Insets(10, 50, 0, 10); // Set spacing
        gbc.anchor = GridBagConstraints.PAGE_START;

        title = new JLabel("A Breakdown of Selected Meal's Nutrients");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        //title.setSize(500, 30);
        //title.setLocation(250, 30);
        labelsPanel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        dietNumberLabel = new JLabel("Diet Number: " + dietId);
        dietNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        labelsPanel.add(dietNumberLabel,gbc);

        gbc.gridx = 1;
        dateLabel = new JLabel("Date: " + date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        labelsPanel.add(dateLabel, gbc);

        gbc.gridx = 2;
        mealTypeLabel = new JLabel("Meal Type: " + mealType);
        mealTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        labelsPanel.add(mealTypeLabel, gbc);

        c.add(labelsPanel, BorderLayout.NORTH);

        DefaultTableModel nutrientsTableModel = new DefaultTableModel();
        nutrientsTableModel.setColumnIdentifiers(new String[]{"Nutrient", "Quantity"});
        Map<String,String> nutrientsUIData =
                DietsQueryController.getInstance().requestNutrientsDetail(Integer.parseInt(dietId));
        for (Map.Entry<String, String> entry : nutrientsUIData.entrySet()) {
            nutrientsTableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
        nutrientTable = new JTable(nutrientsTableModel);
        JTableHeader header = nutrientTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(nutrientTable);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        c.add(tablePanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.addActionListener(this);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        c.add(backButton, BorderLayout.SOUTH);


        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            // Handle the back button action here.
            dispose();
        }
    }

    public static void launch(String dietId, String date, String mealType){
        DietDetailPage ddp = new DietDetailPage(dietId, date, mealType);
        ddp.setSize(1280,720);
        ddp.setVisible(true);
    }
}