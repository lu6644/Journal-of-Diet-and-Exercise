package View.ProfileUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import Model.Profile.UserProfile;

import View.MainUI.NavigateUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileDetailsPage extends JFrame {

    private ProfileUIData user;

    public ProfileDetailsPage(ProfileUIData userProfile) {
        this.user = userProfile;
        createComponents();
    }

    // Method to create UI components
    private void createComponents() {
        // Setting up JFrame properties
        setTitle("Profile Details");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        // Coordinates for positioning components
        int labelX = 50;
        int textFieldX = 200;
        int startY = 30;
        int ySpacing = 30;



        // Displaying profile details
        addField("Account ID: ", new JLabel("" + user.getId()), labelX, textFieldX, startY, ySpacing);
        addField("Username: ", new JLabel(user.getUsername()), labelX, textFieldX, startY + ySpacing, ySpacing);
        addField("FirstName: ", new JLabel(user.getFirstName()), labelX, textFieldX, startY + 2 * ySpacing, ySpacing);
        addField("Last Name:", new JLabel(user.getLastName()), labelX, textFieldX, startY + 3 * ySpacing, ySpacing);
        addField("Age:", new JLabel("" +user.getAge()), labelX, textFieldX, startY + 4 * ySpacing, ySpacing);
        addField("Gender:", new JLabel(user.getGender()), labelX, textFieldX, startY + 5 * ySpacing, ySpacing);
        addField("Height:", new JLabel(user.getHeight() + " " + user.getHeightUnit()), labelX, textFieldX, startY + 6 * ySpacing, ySpacing);
        addField("Weight:", new JLabel(user.getWeight() + " " + user.getWeightUnit()), labelX, textFieldX, startY + 7 * ySpacing, ySpacing);
        addField("Special period:", new JLabel(user.getSpecialPeriod()), labelX, textFieldX, startY + 8 * ySpacing, ySpacing);
        addField("Have weight scale:", new JLabel(user.isHasWeightScale() ? "Yes" : "No"), labelX, textFieldX, startY + 9 * ySpacing, ySpacing);

        // Adding buttons with ActionListener
        JButton editButton = new JButton("Edit");
        JButton cancelButton = new JButton("Back");

        editButton.setBounds(labelX, startY + 10 * ySpacing, 100, 30);
        cancelButton.setBounds(labelX + 150, startY + 10 *ySpacing, 100, 30);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });
        cancelButton.addActionListener(e -> {
            this.dispose();
            NavigateUI.launch(user);
        });

        add(editButton);
        add(cancelButton);

        // Making the frame visible
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void editAction() {
        this.dispose();

        ProfileEditPage.launch(user);
    }

    // Method to add labels and data
    private void addField(String label, JLabel data, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);

        data.setBounds(textFieldX, y, 150, 20);
        add(data);
    }

    // Method to launch the ProfileDetailsPage
    public static void launch(ProfileUIData user){new ProfileDetailsPage(user);}

}
