package View.DietExerciseDataUI;
import Controller.DataLoggingHandler.DietLoggingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DietLoggingPage extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel date;
    private JTextField tdate;
    private JLabel meal;
    //private JTextField tmno;
    private JComboBox cmeal;
    private JLabel food1;
    private JTextField food1t;
    private JLabel qty1;
    private JTextField qty1t;
    private JLabel food2;
    private JTextField food2t;
    private JLabel qty2;
    private JTextField qty2t;
    private JLabel food3;
    private JTextField food3t;
    private JLabel qty3;
    private JTextField qty3t;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dob;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;
    private JLabel resultHeading;
    private JLabel result;

    private String meals[]
            = {"breakfast", "lunch", "dinner", "snack"};

    // constructor, to initialize the components
    // with default values.

    public static void launch(){
        DietLoggingPage dlp = new DietLoggingPage();
        dlp.setSize(1280,720);
        dlp.setVisible(true);
    }

    public DietLoggingPage()
    {
        setTitle("Log Your Diet");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Record Your Diet");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(500, 40);
        title.setLocation(400, 30);
        c.add(title);

        date = new JLabel("Date:");
        date.setFont(new Font("Arial", Font.PLAIN, 20));
        date.setSize(100, 30);
        date.setLocation(100, 100);
        c.add(date);

        tdate = new JTextField();
        tdate.setFont(new Font("Arial", Font.PLAIN, 15));
        tdate.setSize(190, 30);
        tdate.setLocation(250, 100);
        c.add(tdate);

        meal = new JLabel("Meal Type:");
        meal.setFont(new Font("Arial", Font.PLAIN, 20));
        meal.setSize(100, 30);
        meal.setLocation(100, 150);
        c.add(meal);

        cmeal = new JComboBox(meals);
        cmeal.setFont(new Font("Arial", Font.PLAIN, 15));
        cmeal.setSize(190, 30);
        cmeal.setLocation(250, 150);
        c.add(cmeal);

        food1 = new JLabel("Food1 Name:");
        food1.setFont(new Font("Arial", Font.PLAIN, 20));
        food1.setSize(140, 30);
        food1.setLocation(100, 200);
        c.add(food1);

        food1t = new JTextField();
        food1t.setFont(new Font("Arial", Font.PLAIN, 15));
        food1t.setSize(190, 30);
        food1t.setLocation(250, 200);
        c.add(food1t);

        qty1 = new JLabel("Food1 Quantity(g):");
        qty1.setFont(new Font("Arial", Font.PLAIN, 20));
        qty1.setSize(180, 30);
        qty1.setLocation(500, 200);
        c.add(qty1);

        qty1t = new JTextField();
        qty1t.setFont(new Font("Arial", Font.PLAIN, 15));
        qty1t.setSize(190, 30);
        qty1t.setLocation(700, 200);
        c.add(qty1t);

        food2 = new JLabel("Food2 Name:");
        food2.setFont(new Font("Arial", Font.PLAIN, 20));
        food2.setSize(140, 30);
        food2.setLocation(100, 250);
        c.add(food2);

        food2t = new JTextField();
        food2t.setFont(new Font("Arial", Font.PLAIN, 15));
        food2t.setSize(190, 30);
        food2t.setLocation(250, 250);
        c.add(food2t);

        qty2 = new JLabel("Food2 Quantity(g):");
        qty2.setFont(new Font("Arial", Font.PLAIN, 20));
        qty2.setSize(180, 30);
        qty2.setLocation(500, 250);
        c.add(qty2);

        qty2t = new JTextField();
        qty2t.setFont(new Font("Arial", Font.PLAIN, 15));
        qty2t.setSize(190, 30);
        qty2t.setLocation(700, 250);
        c.add(qty2t);

        food3 = new JLabel("Food3 Name:");
        food3.setFont(new Font("Arial", Font.PLAIN, 20));
        food3.setSize(140, 30);
        food3.setLocation(100, 300);
        c.add(food3);

        food3t = new JTextField();
        food3t.setFont(new Font("Arial", Font.PLAIN, 15));
        food3t.setSize(190, 30);
        food3t.setLocation(250, 300);
        c.add(food3t);

        qty3 = new JLabel("Food3 Quantity(g):");
        qty3.setFont(new Font("Arial", Font.PLAIN, 20));
        qty3.setSize(180, 30);
        qty3.setLocation(500, 300);
        c.add(qty3);

        qty3t = new JTextField();
        qty3t.setFont(new Font("Arial", Font.PLAIN, 15));
        qty3t.setSize(190, 30);
        qty3t.setLocation(700, 300);
        c.add(qty3t);

        term = new JCheckBox("Accept Terms And Conditions.");
        term.setFont(new Font("Arial", Font.PLAIN, 15));
        term.setSize(250, 20);
        term.setLocation(150, 350);
        c.add(term);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 400);
        sub.setActionCommand("submit");
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 400);
        reset.setActionCommand("reset");
        reset.addActionListener(this);
        c.add(reset);

        resultHeading = new JLabel("Your nutrient intake for this meal:");
        resultHeading.setFont(new Font("Arial", Font.BOLD, 20));
        resultHeading.setForeground(Color.GREEN);
        resultHeading.setSize(600, 30);
        resultHeading.setLocation(100, 450);
        resultHeading.setVisible(false);
        c.add(resultHeading);

        result = new JLabel("");
        result.setFont(new Font("Arial", Font.ITALIC, 15));
        result.setForeground(Color.GREEN);
        result.setSize(600, 150);
        result.setLocation(100, 480);
        c.add(result);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String comm = e.getActionCommand();
        if (comm.equals("submit")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
            Date inputDate = null;
            try {
                inputDate = formatter.parse(tdate.getText().strip());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "wrong date type");
            }
            String inputMeal = cmeal.getSelectedItem().toString();
            String inputFood1 = food1t.getText();
            Double inputQty1 = Double.parseDouble(qty1t.getText());
            String inputFood2 = food2t.getText();
            Double inputQty2 = Double.parseDouble(qty2t.getText());
            String inputFood3 = food3t.getText();
            Double inputQty3 = Double.parseDouble(qty3t.getText());
            HashMap<String, Double> foods = new HashMap<>();
            foods.put(inputFood1, inputQty1);
            foods.put(inputFood2, inputQty2);
            foods.put(inputFood3, inputQty3);

            DietLoggingController c = DietLoggingController.getInstance();
            String nutrientInfo = c.logDiet(inputDate,inputMeal,foods);
            resultHeading.setVisible(true);
            result.setText("<html>" + nutrientInfo.replaceAll("\n", "<br>"));
        }
    }





}

