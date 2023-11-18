package View.ExerciseLoggingUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import Model.Profile.UserProfile;

public class ExerciseLoggingUI extends JFrame implements ActionListener {
	JComboBox<String> exerciseComboBox;
	JComboBox<String> intensityComboBox;
	UserProfile user;
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

	ExerciseLoggingUI(UserProfile user) {
		this.user = user;
		accountInfo = new JLabel("AccountID: 000001");
		head = new JLabel("Excercise Logging System");
		String[] exerciseOptions = { "Swimming", "Running", "Biking", "Walking", "Calisthenics", "Basketball" };
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
						String[] intensityOptions = { "vigorous effort", "light or moderate effort" };
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Running")) {
						String[] intensityOptions = { "5 mph (12 min/mile)", "7 mph (8.5 min/mile)",
								"10 mph (6 min/mile)" };
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Biking")) {
						String[] intensityOptions = { "for pleasure", "light effort", "moderate effort",
								"racing or vigorous effort" };
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Walking")) {
						String[] intensityOptions = { "moderate pace", "for exercise", "very brisk pace" };
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Calisthenics")) {
						String[] intensityOptions = { "light effort", "moderate effort", "vigorous effort" };
						intensityComboBox.setModel(new DefaultComboBoxModel<>(intensityOptions));
					} else if (selectedOption.equals("Basketball")) {
						String[] intensityOptions = { "game", "general", "shooting baskets" };
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
		b.setBounds(350, 700, 150, 50);
		b.addActionListener(this);
		date = new JLabel("Date: ");
		dateExample = new JLabel("*Date input example: YYYY-MM-DD");
		dateInput = new JTextField();

		time = new JLabel("Time: ");
		timeNotice = new JLabel("*Please enter time (HH:mm) with 24-hour clock");
		timeInput = new JTextField();

		setSize(1000, 1000);
		head.setBounds(350, 10, 500, 100);
		accountInfo.setBounds(750, 100, 250, 40);
		date.setBounds(25, 150, 100, 40);
		dateInput.setBounds(150, 150, 150, 40);
		dateExample.setBounds(350, 150, 200, 40);
		time.setBounds(25, 250, 100, 40);
		timeInput.setBounds(150, 250, 150, 40);
		timeNotice.setBounds(350, 250, 300, 40);
		exerciseTypeInput.setBounds(25, 350, 100, 40);
		exerciseComboBox.setBounds(150, 350, 150, 40);
		intensityInput.setBounds(25, 450, 100, 40);
		intensityComboBox.setBounds(150, 450, 150, 40);
		notice.setBounds(350, 450, 200, 40);
		durationInput.setBounds(25, 550, 150, 40);
		durationField.setBounds(150, 550, 150, 40);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// Exception handle

		String selectedOption = (String) exerciseComboBox.getSelectedItem();
		String intensity = (String) intensityComboBox.getSelectedItem();
		String enteredDate = dateInput.getText();
		String enteredTime = timeInput.getText();
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

		ExerciseLogging info = new ExerciseLogging(enteredDate, enteredTime, duration, intensity, user);

		if (selectedOption.equals("Swimming")) {
			info.setStrategy(info.new SwimmingStrategy());
		} else if (selectedOption.equals("Running")) {
			info.setStrategy(info.new RunningStrategy());
		} else if (selectedOption.equals("Biking")) {
			info.setStrategy(info.new BikingStrategy());
		} else if (selectedOption.equals("Walking")) {
			info.setStrategy(info.new WalkingStrategy());
		} else if (selectedOption.equals("Calisthenics")) {
			info.setStrategy(info.new CalisthenicsStrategy());
		} else if (selectedOption.equals("Basketball")) {
			info.setStrategy(info.new BasketballStrategy());
		}

		String res = String.format("%.2f", info.calcualteCalories());

		JOptionPane.showMessageDialog(this, "This exercise burned " + res + " calories.");

	}

	public static void launch() {
		// Only for test
		UserProfile user = new UserProfile("test1", "test2", "Test", "Test", 20, "male", 180.0, 75, "", false);
		new ExerciseLoggingUI(user);
	}
}
