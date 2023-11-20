package View.MainUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import View.ProfileUI.ProfileCreationPage;
import View.ProfileUI.ProfileDetailsPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame {

    private JTextField usernameField, passwordField;

    public MainUI(){
        createComponents();
    }

    private void createComponents() {
        setTitle("Home Page");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        int labelX = 150;
        int textFieldX = 250;
        int startY = 150;
        int ySpacing = 30;

        addField("Username:", usernameField = new JTextField(), labelX, textFieldX, startY, ySpacing);
        addField("Password:", passwordField = new JPasswordField(), labelX, textFieldX, startY + ySpacing, ySpacing);
        JButton logInButton = new JButton("Log In");
        logInButton.setBounds(labelX, startY + 2 * ySpacing, 100, 30);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInAction();
            }
        });
        add(logInButton);
        JButton signUpButton = new JButton("Sign up");
        signUpButton.setBounds(labelX + 120, startY + 2 * ySpacing, 100, 30);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpAction();
            }
        });
        add(signUpButton);


        setVisible(true);


    }

    private void addField(String label, JTextField field, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);
        if (field != null) {
            field.setBounds(textFieldX, y, 150, 20);
            add(field);
        }
    }

    public void signUpAction(){
        this.dispose();

        ProfileCreationPage.launch();
    }

    public void logInAction(){
        String username = usernameField.getText();
        String password = new String(((JPasswordField) passwordField).getPassword());

        if(ProfilesQueryController.getInstance().verifyPassword(username, password) == 1){
            this.dispose();
            int id = ProfilesQueryController.getInstance().getProfileByUsername(username, password).getId();
            NavigateUI.launch(ProfilesQueryController.getInstance().getProfile(id));
        }
        else{
            JOptionPane.showMessageDialog(this,"Logging Information is incorrect!", "Wrong username or password",JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void launch(){new MainUI();}

    public static void main(String args[]){
        launch();
    }
}
