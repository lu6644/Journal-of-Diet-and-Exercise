package View.MainUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import Model.Profile.UserProfile;
import View.DataVisulizationUI.CFGComparisionPage;
import View.DietExerciseDataUI.DietLoggingPage;
import View.ExerciseLoggingUI.ExerciseLoggingUI;
import View.ProfileUI.ProfileDetailsPage;

import javax.swing.*;

public class NavigateUI extends JFrame {

    private int account_id;

    public NavigateUI(int account_id) {
        this.account_id = account_id;
        createComponents();
    }

    private void createComponents() {
        setTitle("Navigate Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        int labelX = 440;
        int startY = 50;
        int ySpacing = 50;

        JLabel l = new JLabel("Welcome! " + ProfilesQueryController.getInstance().getUsername(account_id));
        l.setBounds(labelX+130, startY, 200, 40);
        add(l);

        JButton infoButton = new JButton("Account Information");
        infoButton.setBounds(labelX, startY + 2 * ySpacing, 400, 40);
        infoButton.addActionListener(e -> infoAction());
        add(infoButton);

        JButton exLoggingButton = new JButton("Exercise Logging");
        exLoggingButton.setBounds(labelX, startY + 3 * ySpacing, 400, 40);
        exLoggingButton.addActionListener(e->exLoggingAction());
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
        DietLoggingPage.launch(account_id);
    }
    public void infoAction(){
        this.dispose();
        ProfileDetailsPage.launch(account_id);
    }

    public void exLoggingAction(){
        this.dispose();
        ExerciseLoggingUI.launch(account_id);
    }

    public void CFGCompareAction() {
        this.dispose();
        CFGComparisionPage.launch(account_id);
    }

    public static void launch(int account_id) {
        new NavigateUI(account_id);
    }
}
