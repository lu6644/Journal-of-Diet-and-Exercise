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

    // Method to create UI components
    private void createComponents() {
        // Setting up JFrame properties
        setTitle("Home Page");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Coordinates for positioning components
        int labelX = 150;
        int textFieldX = 250;
        int startY = 150;
        int ySpacing = 30;

        // Adding text fields for username and password
        addField("Username:", usernameField = new JTextField(), labelX, textFieldX, startY, ySpacing);
        addField("Password:", passwordField = new JPasswordField(), labelX, textFieldX, startY + ySpacing, ySpacing);

        // Adding login button with ActionListener
        JButton logInButton = new JButton("Log In");
        logInButton.setBounds(labelX, startY + 2 * ySpacing, 100, 30);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInAction();
            }
        });
        add(logInButton);

        // Adding sign-up button with ActionListener
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

        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }
    // Method to add labels and text fields
    private void addField(String label, JTextField field, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);
        if (field != null) {
            field.setBounds(textFieldX, y, 150, 20);
            add(field);
        }
    }
    // Method to handle sign-up action
    public void signUpAction(){
        this.dispose();

        ProfileCreationPage.launch();
    }
    // Method to handle log-in action
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

    // Method to launch the MainUI
    public static void launch(){new MainUI();}

    // Main method to launch the application
    public static void main(String args[]){
        launch();
    }
}
