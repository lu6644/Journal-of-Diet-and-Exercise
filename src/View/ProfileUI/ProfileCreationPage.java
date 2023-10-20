package View.ProfileUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Profile.UserProfile;

public class ProfileCreationPage extends JFrame{
	private UserProfile userProfile;
	private JTextField usernameField, passwordField, firstNameField, lastNameField, ageField, genderField, heightField, weightField, specialPeriodField;
	private JCheckBox hasWeightScaleCheckBox;
	
	
	public ProfileCreationPage() {
		createComponents();
	}
	
	private void createComponents() {
		setTitle("Profile Creation");
		setSize(1280,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(11,2));
	
	
	
	usernameField = new JTextField();
	passwordField = new JPasswordField(); 
	firstNameField = new JTextField();
	lastNameField = new JTextField();
	ageField = new JTextField();
	genderField = new JTextField();
	heightField = new JTextField();
	weightField = new JTextField();
	specialPeriodField = new JTextField();
	hasWeightScaleCheckBox = new JCheckBox("Do you have weight scale?");
	
	
	JButton submitButton = new JButton("Submit");
	submitButton.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e) {
			submitAction();
		}
	});
	
	add(new JLabel("Username:")); add(usernameField);
	add(new JLabel("Password:")); add(passwordField);
	add(new JLabel("First Name:")); add(firstNameField);
	add(new JLabel("Last Name:")); add(lastNameField);
	add(new JLabel("Age:")); add(ageField);
	add(new JLabel("Gender:")); add(genderField);
	add(new JLabel("Height(cm):")); add(heightField);
	add(new JLabel("Weight(kg):")); add(weightField);
	add(new JLabel("Special Period:")); add(specialPeriodField);
	add(hasWeightScaleCheckBox); add(submitButton);
	
	
	setVisible(true);
}
	
	private void submitAction() {
		String username = usernameField.getText();
		String password = new String(((JPasswordField)passwordField).getPassword());
		String firstName = firstNameField.getText();
		String lastName = firstNameField.getText();
		int age = Integer.parseInt(ageField.getText());
		String gender = genderField.getText();
		double height = Double.parseDouble(heightField.getText());
		double weight = Double.parseDouble(weightField.getText());
		String specialPeriod = specialPeriodField.getText();
		boolean hasWeightScale = hasWeightScaleCheckBox.isSelected();
		
		userProfile = new UserProfile(username,password, firstName, lastName, age, gender,height,weight,specialPeriod,hasWeightScale);
		
		System.out.println( username + ": Your profile has been created " );
	}
	
	public static void main(String[] args) {
		new ProfileCreationPage();
	}
	
	
	
}
