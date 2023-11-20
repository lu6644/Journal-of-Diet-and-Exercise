package View.ProfileUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import Model.Profile.UserProfile;
import View.MainUI.NavigateUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileDetailsPage extends JFrame {
    private int account_id;

    public ProfileDetailsPage(int account_id) {
        this.account_id = account_id;
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
        addField("Account ID: ", new JLabel("" + account_id), labelX, textFieldX, startY, ySpacing);
        addField("Username: ", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).getUsername()), labelX, textFieldX, startY + ySpacing, ySpacing);
        addField("FirstName: ", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).getFirstName()), labelX, textFieldX, startY + 2 * ySpacing, ySpacing);
        addField("Last Name:", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).getLastName()), labelX, textFieldX, startY + 3 * ySpacing, ySpacing);
        addField("Age:", new JLabel("" + ProfilesQueryController.getInstance().getProfile(account_id).getAge()), labelX, textFieldX, startY + 4 * ySpacing, ySpacing);
        addField("Gender:", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).getGender()), labelX, textFieldX, startY + 5 * ySpacing, ySpacing);
        addField("Height:", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).getHeight() + " " + ProfilesQueryController.getInstance().getProfile(account_id).getHeightUnit()), labelX, textFieldX, startY + 6 * ySpacing, ySpacing);
        addField("Weight:", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).getWeight() + " " + ProfilesQueryController.getInstance().getProfile(account_id).getWeightUnit()), labelX, textFieldX, startY + 7 * ySpacing, ySpacing);
        addField("Special period:", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).getSpecialPeriod()), labelX, textFieldX, startY + 8 * ySpacing, ySpacing);
        addField("Have weight scale:", new JLabel(ProfilesQueryController.getInstance().getProfile(account_id).isHasWeightScale() ? "Yes" : "No"), labelX, textFieldX, startY + 9 * ySpacing, ySpacing);

        JButton editButton = new JButton("Edit");
        JButton backButton = new JButton("Back");
        editButton.setBounds(labelX, startY + 10 * ySpacing, 100, 30);
        backButton.setBounds(labelX + 150, startY + 10 *ySpacing, 100, 30);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAction();
            }
        });



        add(editButton);
        add(backButton);
        setVisible(true);
    }

    private void editAction() {
        this.dispose();

        ProfileEditPage.launch(account_id);
    }

    private void backAction(){
        this.dispose();

        NavigateUI.launch(account_id);
    }

    private void addField(String label, JLabel data, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);

        data.setBounds(textFieldX, y, 150, 20);
        add(data);
    }

    public static void launch(int account_id){new ProfileDetailsPage(account_id);}

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
        new ProfileDetailsPage(8);
    }
}
