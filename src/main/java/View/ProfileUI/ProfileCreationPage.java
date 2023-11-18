package View.ProfileUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Model.Profile.UserProfile;

public class ProfileCreationPage extends JFrame {
    private UserProfile userProfile;
    private JTextField usernameField, passwordField, firstNameField, lastNameField, ageField, genderField, heightField,
            weightField, specialPeriodField;
    private JCheckBox hasWeightScaleCheckBox;

    private JRadioButton kgRadioButton, lbsRadioButton, cmRadioButton, inchRadioButton, hasSpecialPeriod;

    public ProfileCreationPage() {
        createComponents();
    }

    private void createComponents() {
        setTitle("Profile Creation");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        int labelX = 50;
        int textFieldX = 200;
        int startY = 30;
        int ySpacing = 30;

        addField("Username:", usernameField = new JTextField(), labelX, textFieldX, startY, ySpacing);
        addField("Password:", passwordField = new JPasswordField(), labelX, textFieldX, startY + ySpacing, ySpacing);
        addField("First Name:", firstNameField = new JTextField(), labelX, textFieldX, startY + 2 * ySpacing, ySpacing);
        addField("Last Name:", lastNameField = new JTextField(), labelX, textFieldX, startY + 3 * ySpacing, ySpacing);
        addField("Age:", ageField = new JTextField(), labelX, textFieldX, startY + 4 * ySpacing, ySpacing);
        addField("Gender:", genderField = new JTextField(), labelX, textFieldX, startY + 5 * ySpacing, ySpacing);
        addField("Height:", heightField = new JTextField(), labelX, textFieldX, startY + 6 * ySpacing, ySpacing);

        cmRadioButton = new JRadioButton("cm");
        cmRadioButton.setBounds(textFieldX + 160, startY + 6 * ySpacing, 60, 20);
        add(cmRadioButton);

        inchRadioButton = new JRadioButton("inches");
        inchRadioButton.setBounds(textFieldX + 220, startY + 6 * ySpacing, 90, 20);
        add(inchRadioButton);

        ButtonGroup heightUnitGroup = new ButtonGroup();
        heightUnitGroup.add(cmRadioButton);
        heightUnitGroup.add(inchRadioButton);
        cmRadioButton.setSelected(true);

        addField("Weight:", weightField = new JTextField(), labelX, textFieldX, startY + 7 * ySpacing, ySpacing);

        kgRadioButton = new JRadioButton("kg");
        kgRadioButton.setBounds(textFieldX + 160, startY + 7 * ySpacing, 60, 20);
        add(kgRadioButton);

        lbsRadioButton = new JRadioButton("lbs");
        lbsRadioButton.setBounds(textFieldX + 220, startY + 7 * ySpacing, 90, 20);
        add(lbsRadioButton);

        ButtonGroup weightUnitGroup = new ButtonGroup();
        weightUnitGroup.add(kgRadioButton);
        weightUnitGroup.add(lbsRadioButton);
        kgRadioButton.setSelected(true);

        addField("Special Period:", specialPeriodField = new JTextField(), labelX, textFieldX, startY + 8 * ySpacing, ySpacing);

        hasSpecialPeriod = new JRadioButton("None");
        hasSpecialPeriod.setBounds(textFieldX + 160, startY + 8 * ySpacing, 90, 20);
        hasSpecialPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specialPeriodField.setEnabled(!hasSpecialPeriod.isSelected());
            }
        });

        add(hasSpecialPeriod);

        hasWeightScaleCheckBox = new JCheckBox("Do you have a weight scale?");
        hasWeightScaleCheckBox.setBounds(labelX, startY + 9 * ySpacing, 300, 20);
        add(hasWeightScaleCheckBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(labelX, startY + 10 * ySpacing, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitAction();
            }
        });
        add(submitButton);

        setVisible(true);
    }

    private void addField(String label, JTextField field, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);

        field.setBounds(textFieldX, y, 150, 20);
        add(field);
    }

    private void submitAction() {
        String username = usernameField.getText();
        String password = new String(((JPasswordField) passwordField).getPassword());
        String firstName = firstNameField.getText();
        String lastName = firstNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = genderField.getText();
        double height = Double.parseDouble(heightField.getText());
        String heightUnit = cmRadioButton.isSelected() ? "cm" : "inches";
        double weight = Double.parseDouble(weightField.getText());
        String weightUnit = kgRadioButton.isSelected() ? "kg" : "lbs";
        String specialPeriod = hasSpecialPeriod.isSelected() ? null : specialPeriodField.getText();
        boolean hasWeightScale = hasWeightScaleCheckBox.isSelected();

        //Convert height to centimeters if the unit is inches
        if ("inches".equals(heightUnit)) {
            height *= 2.54;   //Convert inches to centimeters
        }

        //Convert weight to kilograms if the unit is pounds
        if ("lbs".equals(weightUnit)) {
            weight *= 0.453592; //Convert pounds to kilograms.
        }

        userProfile = new UserProfile(username, password, firstName, lastName, age, gender, height, weight,
                specialPeriod, hasWeightScale);

        System.out.println(username + ": Your profile has been created ");
    }

    public static void launch() {
        new ProfileCreationPage();
    }

}
