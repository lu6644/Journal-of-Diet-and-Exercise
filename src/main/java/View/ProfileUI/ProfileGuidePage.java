package View.ProfileUI;

import Controller.DataRequestHandler.ProfilesQueryController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;

public class ProfileGuidePage extends JFrame{
	
	public ProfileGuidePage() {
        createComponents();
    }

    List<Map<String, String>> profiles;
	
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

        JLabel lProfiles = new JLabel("Existing Profiles:");
        profiles = ProfilesQueryController.getInstance().getProfiles();
        String[] profileNames = profiles.stream().map(entry -> entry.get("name")).toArray(String[]::new);
        JComboBox cprofiles = new JComboBox(profileNames);

        JButton existingProfileButton = new JButton("Choose Existing Profile");
        existingProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedName = cprofiles.getSelectedItem().toString();
                int profileId = Integer.parseInt(profiles.stream().filter(entry ->
                                entry.get("name").equals(selectedName)).map(entry -> entry.get("id"))
                                .findFirst().get());
                System.out.println("selected Profile id:" + profileId);
                //TODO: navigate to MainUI and pass profileId when MainUI is complete
            }
        });

        add(lProfiles);
        add(cprofiles);
        add(existingProfileButton);
        add(createProfileButton);

        setVisible(true);
    }
	public static void main(String[] args) {
        new ProfileGuidePage();
    }
}
