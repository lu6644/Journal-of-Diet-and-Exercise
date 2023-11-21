package View.ProfileUI;

import Controller.DataLoggingHandler.ProfileAddingController;
import View.MainUI.MainUI;
import View.MainUI.NavigateUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ProfileCreationPage extends JFrame {

    // Fields for various user inputs
    private int profileId;
    private JTextField usernameField, passwordField, firstNameField, lastNameField, ageField, heightField,
            weightField, specialPeriodField;
    private JCheckBox hasWeightScaleCheckBox;

    private JRadioButton maleButton, femaleButton;


    private JRadioButton kgRadioButton, lbsRadioButton, cmRadioButton, inchRadioButton, hasSpecialPeriod;

    public ProfileCreationPage() {
        createComponents();
    }

    // Method to create UI components
    private void createComponents() {
        // Setting up JFrame properties
        setTitle("Profile Creation");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Coordinates for positioning components
        int labelX = 50;
        int textFieldX = 200;
        int startY = 30;
        int ySpacing = 30;

        // Adding various input fields and labels
        addField("Username:", usernameField = new JTextField(), labelX, textFieldX, startY, ySpacing);
        addField("Password:", passwordField = new JPasswordField(), labelX, textFieldX, startY + ySpacing, ySpacing);
        addField("First Name:", firstNameField = new JTextField(), labelX, textFieldX, startY + 2 * ySpacing, ySpacing);
        addField("Last Name:", lastNameField = new JTextField(), labelX, textFieldX, startY + 3 * ySpacing, ySpacing);
        addField("Age:", ageField = new JTextField(), labelX, textFieldX, startY + 4 * ySpacing, ySpacing);
        addField("Gender:", null, labelX, textFieldX, startY + 5 * ySpacing, ySpacing);
        maleButton = new JRadioButton("male");
        maleButton.setBounds(textFieldX, startY + 5 * ySpacing, 70, 20);
        add(maleButton);

        femaleButton = new JRadioButton("female");
        femaleButton.setBounds(textFieldX + 80, startY + 5 * ySpacing, 90, 20);
        add(femaleButton);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

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

        JButton backButton = new JButton("Back");
        backButton.setBounds(labelX + 150, startY + 10 *ySpacing, 100, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAction();
            }
        });
        add(backButton);

        setVisible(true);
    }
    // Method to add labels and input fields
    private void addField(String label, JTextField field, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);
        if (field != null) {
            field.setBounds(textFieldX, y, 150, 20);
            add(field);
        }
    }

    // Method to handle submit action
    private void submitAction() {
        // Retrieving user inputs
        String username = usernameField.getText();
        String password = new String(((JPasswordField) passwordField).getPassword());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = maleButton.isSelected()? "male":"female";
        double height = Double.parseDouble(heightField.getText());
        String heightUnit = cmRadioButton.isSelected() ? "cm" : "inches";
        double weight = Double.parseDouble(weightField.getText());
        String weightUnit = kgRadioButton.isSelected() ? "kg" : "lbs";
        String specialPeriod = hasSpecialPeriod.isSelected() ? null : specialPeriodField.getText();
        boolean hasWeightScale = hasWeightScaleCheckBox.isSelected();

        // Adding a new profile using the controller
        profileId = ProfileAddingController.getInstance().addNewProfile(username, password, firstName, lastName, age, gender, height, heightUnit,weight,weightUnit,specialPeriod, hasWeightScale);

        // Displaying success message
        JOptionPane.showMessageDialog(this, "Profile created sucessfully!\nprofileID generated: " + profileId, "Success", JOptionPane.INFORMATION_MESSAGE);

        this.dispose();

        // Creating a ProfileUIData object with user details
        ProfileUIData user = new ProfileUIData(username, password, firstName, lastName, age, gender, height, heightUnit,weight,weightUnit,specialPeriod, hasWeightScale);
        user.setId(profileId);

        NavigateUI.launch(user);


    }

    // Method to handle back action
    public void backAction(){
        this.dispose();
        MainUI.launch();
    }

    public static void launch() {
        new ProfileCreationPage();
    }

}
