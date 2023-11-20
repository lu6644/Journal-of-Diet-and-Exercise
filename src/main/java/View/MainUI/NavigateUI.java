package View.MainUI;

import Model.Profile.UserProfile;
import View.DietExerciseDataUI.DietLoggingPage;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.ProfileUI.ProfileDetailsPage;

import javax.swing.*;

public class NavigateUI extends JFrame {

    private UserProfile user;

    public NavigateUI(UserProfile user) {
        this.user = user;
        createComponents();
    }

    private void createComponents() {
        setTitle("Navigate Page");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        int labelX = 200;
        int textFieldX = 250;
        int startY = 50;
        int ySpacing = 50;

        JLabel l = new JLabel("Welcome! " + user.getUsername());
        l.setBounds(labelX+30, startY, 200, 40);
        add(l);

        JButton infoButton = new JButton("Account Information");
        infoButton.setBounds(labelX, startY + 2 * ySpacing, 200, 40);
        add(infoButton);

        JButton exLoggingButton = new JButton("Exercise Logging");
        exLoggingButton.setBounds(labelX, startY + 3 * ySpacing, 200, 40);
        add(exLoggingButton);

        JButton dietLoggingButton = new JButton("Diet Logging");
        dietLoggingButton.setBounds(labelX, startY + 4 * ySpacing, 200, 40);
        add(dietLoggingButton);

        setVisible(true);
    }


    public void dietloggingAction(){
        this.dispose();
        DietLoggingPage.launch();
    }
    public void infoAction(){
        this.dispose();
        ProfileDetailsPage.launch(user);
    }

    public void exLoggingAction(){
        this.dispose();
        ExerciseLoggingUI.launch(user.getId());
    }

    public static void launch(UserProfile user) {
        new NavigateUI(user);
    }
}
