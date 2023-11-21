// Import statements for necessary classes
package View.ProfileUI;

import Controller.DataRequestHandler.ProfilesQueryController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;

// JFrame for guiding the user to create or choose an existing profile
public class ProfileGuidePage extends JFrame {

    // List to store profiles retrieved from the database
    List<Map<String, String>> profiles;

    // Constructor to create the ProfileGuidePage
    public ProfileGuidePage() {
        createComponents();
    }

    // Method to create GUI components
    private void createComponents() {
        // Set JFrame properties
        setTitle("Profile Guide Page");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create "Create New Profile" button with action listener
        JButton createProfileButton = new JButton("Create New Profile");
        createProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the ProfileCreationPage when the button is clicked
                new ProfileCreationPage();
                setVisible(false); // Hide the current JFrame
            }
        });

        // Create label and dropdown menu for existing profiles
        JLabel lProfiles = new JLabel("Existing Profiles:");
        profiles = ProfilesQueryController.getInstance().getProfiles();
        String[] profileNames = profiles.stream().map(entry -> entry.get("name")).toArray(String[]::new);
        JComboBox<String> cprofiles = new JComboBox<>(profileNames);

        // Create "Choose Existing Profile" button with action listener
        JButton existingProfileButton = new JButton("Choose Existing Profile");
        existingProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve the selected profile name from the dropdown menu
                String selectedName = cprofiles.getSelectedItem().toString();

                // Find the profile ID corresponding to the selected name
                int profileId = Integer.parseInt(profiles.stream().filter(entry ->
                                entry.get("name").equals(selectedName)).map(entry -> entry.get("id"))
                        .findFirst().get());

                // Print the selected profile ID (you can modify this part based on your needs)
                System.out.println("Selected Profile ID: " + profileId);

                // TODO: Navigate to MainUI and pass profileId when MainUI is complete
            }
        });

        // Add components to the JFrame
        add(lProfiles);
        add(cprofiles);
        add(existingProfileButton);
        add(createProfileButton);

        // Make the JFrame visible
        setVisible(true);
    }

    // Main method to launch the ProfileGuidePage
    public static void main(String[] args) {
        new ProfileGuidePage();
    }
}
