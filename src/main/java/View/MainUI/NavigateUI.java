package View.MainUI;

import View.DataVisulizationUI.CFGComparisionPage;
import View.DietExerciseDataUI.DietLoggingPage;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.ProfileUI.ProfileDetailsPage;
import View.ProfileUI.ProfileUIData;

import javax.swing.*;

public class NavigateUI extends JFrame {

    private ProfileUIData user;

    public NavigateUI(ProfileUIData user) {
        this.user = user;
        createComponents();
    }

    private void createComponents() {
        setTitle("Navigate Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        int labelX = 440;
        int textFieldX = 250;
        int startY = 50;
        int ySpacing = 50;

        JLabel l = new JLabel("Welcome! " + user.getUsername());
        l.setBounds(labelX+130, startY, 200, 40);
        add(l);

        JButton infoButton = new JButton("Account Information");
        infoButton.setBounds(labelX, startY + 2 * ySpacing, 400, 40);
        infoButton.addActionListener(e -> infoAction());
        add(infoButton);

        JButton exLoggingButton = new JButton("Exercise Logging");
        exLoggingButton.setBounds(labelX, startY + 3 * ySpacing, 400, 40);
        add(exLoggingButton);

        JButton dietLoggingButton = new JButton("Diet Logging");
        dietLoggingButton.setBounds(labelX, startY + 4 * ySpacing, 400, 40);
        add(dietLoggingButton);

        JButton CFGCompareButton = new JButton("Compare My Food Intake With CFG Recommendations");
        CFGCompareButton.setBounds(labelX, startY + 5 * ySpacing, 400, 40);
        CFGCompareButton.addActionListener(e -> CFGCompareAction());
        add(CFGCompareButton);

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

    public void CFGCompareAction() {
        this.dispose();
        CFGComparisionPage.launch(user.getId());
    }

    public static void launch(ProfileUIData user) {
        new NavigateUI(user);
    }
}
