package View.ProfileUI;
import Model.Profile.UserProfile;

import javax.swing.*;
import java.awt.*;

public class ProfileDetailsPage extends JFrame{
	private UserProfile userProfile;

    public ProfileDetailsPage(UserProfile userProfile) {
        this.userProfile = userProfile;
        createComponents();
}
    
    private void createComponents() {
        setTitle("Profile Details");
        setSize(640, 480);
        setLayout(new GridLayout(0, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Display profile details
        add(new JLabel("Username:")); add(new JLabel(userProfile.getUsername()));
        add(new JLabel("First Name:")); add(new JLabel(userProfile.getFirstName()));
        add(new JLabel("Last Name:")); add(new JLabel(userProfile.getLastName()));
        add(new JLabel("Age:")); add(new JLabel(String.valueOf(userProfile.getAge())));
        add(new JLabel("Gender:")); add(new JLabel(userProfile.getGender()));
        add(new JLabel("Height:")); add(new JLabel(String.valueOf(userProfile.getHeight())));
        add(new JLabel("Weight:")); add(new JLabel(String.valueOf(userProfile.getWeight())));
        add(new JLabel("Special Period:")); add(new JLabel(userProfile.getSpecialPeriod()));
        add(new JLabel("Has Weight Scale:")); add(new JLabel(userProfile.isHasWeightScale() ? "Yes" : "No"));

        setVisible(true);
    }
}
