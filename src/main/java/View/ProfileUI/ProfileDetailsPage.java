package View.ProfileUI;

import Model.Profile.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileDetailsPage extends JFrame {
    private UserProfile userProfile;

    public ProfileDetailsPage(UserProfile userProfile) {
        this.userProfile = userProfile;
        createComponents();
    }

    private void createComponents() {
        setTitle("Profile Details");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        int labelX = 50;
        int textFieldX = 200;
        int startY = 30;
        int ySpacing = 30;

        // Display profile details
        addField("Account ID: ", new JLabel("" + userProfile.getId()), labelX, textFieldX, startY, ySpacing);
        addField("Username: ", new JLabel(userProfile.getUsername()), labelX, textFieldX, startY + ySpacing, ySpacing);
        addField("FirstName: ", new JLabel(userProfile.getFirstName()), labelX, textFieldX, startY + 2 * ySpacing, ySpacing);
        addField("Last Name:", new JLabel(userProfile.getLastName()), labelX, textFieldX, startY + 3 * ySpacing, ySpacing);
        addField("Age:", new JLabel("" + userProfile.getAge()), labelX, textFieldX, startY + 4 * ySpacing, ySpacing);
        addField("Gender:", new JLabel(userProfile.getGender()), labelX, textFieldX, startY + 5 * ySpacing, ySpacing);
        addField("Height:", new JLabel(userProfile.getHeight() + " " + userProfile.getHeightUnit()), labelX, textFieldX, startY + 6 * ySpacing, ySpacing);
        addField("Weight:", new JLabel(userProfile.getWeight() + " " + userProfile.getWeightUnit()), labelX, textFieldX, startY + 7 * ySpacing, ySpacing);
        addField("Special period:", new JLabel(userProfile.getSpecialPeriod()), labelX, textFieldX, startY + 8 * ySpacing, ySpacing);
        addField("Have weight scale:", new JLabel(userProfile.isHasWeightScale() ? "Yes" : "No"), labelX, textFieldX, startY + 9 * ySpacing, ySpacing);

        JButton editButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");
        editButton.setBounds(labelX, startY + 10 * ySpacing, 100, 30);
        cancelButton.setBounds(labelX + 150, startY + 10 *ySpacing, 100, 30);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });

        add(editButton);
        add(cancelButton);
        setVisible(true);
    }

    private void editAction() {
        this.dispose();

        ProfileEditPage.launch(userProfile);
    }

    private void addField(String label, JLabel data, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);

        data.setBounds(textFieldX, y, 150, 20);
        add(data);
    }

    public static void launch(UserProfile user){new ProfileDetailsPage(user);}

    public static void main(String args[]) {
        UserProfile user = new UserProfile();
        user.setId(8);
        user.setFirstName("test");
        user.setLastName("test");
        user.setAge(25);
        user.setGender("male");
        user.setHeight(60);
        user.setHeightUnit("inches");
        user.setWeight(170);
        user.setWeightUnit("lbs");
        user.setSpecialPeriod("Test");
        user.setHasWeightScale(false);
        new ProfileDetailsPage(user);
    }
}
