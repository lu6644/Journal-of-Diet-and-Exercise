package View.ProfileUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileGuidePage extends JFrame{
	
	public ProfileGuidePage() {
        createComponents();
    }
	
	private void createComponents() {
        setTitle("Profile Guide Page");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton createProfileButton = new JButton("Create New Profile");
        createProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ProfileCreationPage();
                setVisible(false); 
            }
        });
        JButton existingProfileButton = new JButton("Choose Existing Profile");
        existingProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "This would open the profile selection interface.");
            }
        });
        add(createProfileButton);
        add(existingProfileButton);

        setVisible(true);
    }
	public static void main(String[] args) {
        new ProfileGuidePage();
    }
}
