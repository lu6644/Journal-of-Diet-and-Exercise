package View.ExerciseLoggingUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controller.DataLoggingHandler.ExerciseLogging;

import View.MainUI.NavigateUI;

import View.ProfileUI.ProfileUIData;

import java.text.SimpleDateFormat;

public class ExerciseLoggingUI extends JFrame {
	JComboBox<String> exerciseComboBox;
	JComboBox<String> intensityComboBox;

	ProfileUIData user;

	JLabel exerciseTypeInput;
	JLabel intensityInput;
	JLabel durationInput;
	JLabel accountInfo;
	JLabel head;
	JLabel notice;
	JLabel dateExample;
	JLabel date;
	JLabel time;
	JLabel timeNotice;
	JTextField timeInput;
	JButton b;
	JTextField durationField;
	JTextField dateInput;

	// Constructor that takes a ProfileUIData object as a parameter
	ExerciseLoggingUI(ProfileUIData user) {
		this.user = user;
		// Initialize UI components
		accountInfo = new JLabel("AccountID: " + user.getId());
		head = new JLabel("Excercise Logging System");
		String[] exerciseOptions = {"Swimming", "Running", "Biking", "Walking", "Calisthenics", "Basketball"};
		notice = new JLabel("*Please select exercise type first");
		exerciseComboBox = new JComboBox<>(exerciseOptions);
		exerciseComboBox.setSelectedIndex(-1);
		intensityComboBox = new JComboBox<>();
		intensityComboBox.setEnabled(false);
		exerciseComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedOption = (String) exerciseComboBox.getSelectedItem();
					intensityComboBox.setEnabled(true);
					notice.setVisible(false);
					if (selectedOption.equals("Swimming")) {
						// Take freestyle swimming as an example.
						String[] intensityOptions = {"vigorous effort", "light or moderate effort"};
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Running")) {
						String[] intensityOptions = {"5 mph (12 min/mile)", "7 mph (8.5 min/mile)",
								"10 mph (6 min/mile)"};
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Biking")) {
						String[] intensityOptions = {"for pleasure", "light effort", "moderate effort",
								"racing or vigorous effort"};
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Walking")) {
						String[] intensityOptions = {"moderate pace", "for exercise", "very brisk pace"};
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Calisthenics")) {
						String[] intensityOptions = {"light effort", "moderate effort", "vigorous effort"};
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Basketball")) {
						String[] intensityOptions = {"game", "general", "shooting baskets"};
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					}
				}
				intensityComboBox.setSelectedIndex(-1);
			}
		});
		exerciseTypeInput = new JLabel("Exercise Type: ");
		intensityInput = new JLabel("Intensity: ");
		durationInput = new JLabel("Duration (hour): ");

		durationField = new JTextField();

		b = new JButton("Submit");
		b.setBounds(450, 600, 150, 50);
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});

		JButton backButton = new JButton("Back");
		backButton.setBounds(700, 600, 150, 50);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backAction();
			}
		});
		add(backButton);

		date = new JLabel("Date: ");
		dateExample = new JLabel("*Date input example: YYYY-MM-DD");
		dateInput = new JTextField();

		time = new JLabel("Time: ");
		timeNotice = new JLabel("*Please enter time (HH:mm) with 24-hour clock");
		timeInput = new JTextField();

		// Set layout, visibility, and default close operation
		setSize(1280, 720);
		head.setBounds(450, 10, 500, 100);
		accountInfo.setBounds(850, 100, 250, 40);
		date.setBounds(125, 150, 100, 40);
		dateInput.setBounds(250, 150, 150, 40);
		dateExample.setBounds(450, 150, 300, 40);
		time.setBounds(125, 250, 100, 40);
		timeInput.setBounds(250, 250, 150, 40);
		timeNotice.setBounds(450, 250, 300, 40);
		exerciseTypeInput.setBounds(125, 350, 100, 40);
		exerciseComboBox.setBounds(250, 350, 150, 40);
		intensityInput.setBounds(125, 450, 100, 40);
		intensityComboBox.setBounds(250, 450, 150, 40);
		notice.setBounds(450, 450, 300, 40);
		durationInput.setBounds(125, 550, 150, 40);
		durationField.setBounds(250, 550, 150, 40);

		add(date);
		add(dateInput);
		add(dateExample);
		add(time);
		add(timeInput);
		add(timeNotice);
		add(head);
		add(accountInfo);
		add(exerciseComboBox);
		add(intensityComboBox);
		add(exerciseTypeInput);
		add(intensityInput);
		add(notice);
		add(durationInput);
		add(durationField);
		add(b);

		// The order of setLayout and set Visible can not be exchanged.
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	// Method to handle the "Back" button action
	public void backAction() {
		this.dispose();
		NavigateUI.launch(user);
	}

	// Method to handle the "Submit" button action
	public void action() {


		String selectedOption = (String) exerciseComboBox.getSelectedItem();
		String intensity = (String) intensityComboBox.getSelectedItem();
		String enteredDate = dateInput.getText();
		String enteredTime = timeInput.getText();

		// Validate and process user inputs
		// Check date format
		String datePattern = "\\d{4}-\\d{2}-\\d{2}";
		String timePattern = "\\d{2}:\\d{2}";
		Pattern pattern1 = Pattern.compile(datePattern);
		Pattern pattern2 = Pattern.compile(timePattern);
		Matcher m1 = pattern1.matcher(enteredDate);
		Matcher m2 = pattern2.matcher(enteredTime);
		boolean dateFlag = m1.matches();
		boolean timeFlag = m2.matches();
		if (enteredDate.equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter the date!");
			return;
		} else if (!dateFlag) {
			JOptionPane.showMessageDialog(this, "Please enter the date with correct format!");
			return;
		}

		if (enteredTime.equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter the time!");
			return;
		} else if (!timeFlag) {
			JOptionPane.showMessageDialog(this, "Please enter the time with correct format!");
			return;
		}

		if (selectedOption == null) {
			JOptionPane.showMessageDialog(this, "Please select exercise type!");
			return;
		}

		if (intensity == null) {
			JOptionPane.showMessageDialog(this, "Please select intensity!");
			return;
		}

		try {
			double duration = Double.parseDouble(durationField.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Please enter float type in the duration!");
			return;
		}

		double duration = Double.parseDouble(durationField.getText());

		double caloriesBurnt = ExerciseLogging.getInstance().logExercise(user.getId(), selectedOption, convertToSqlDate(enteredDate, enteredTime), intensity, duration);


		String res = String.format("%.2f", caloriesBurnt);

		JOptionPane.showMessageDialog(this, "Exercise Log Successfully!\nThis exercise burned " + res + " calories.");

	}

	// Method to convert a date and time string to a SQL Date object
	public Date convertToSqlDate(String date, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		String date_time = date + " " + time + ":00";
		try {
			java.util.Date utilDate = sdf.parse(date_time);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			return sqlDate;

		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}

	// Static method to launch the ExerciseLoggingUI
	public static void launch(ProfileUIData user) {
		new ExerciseLoggingUI(user);
	}
}
