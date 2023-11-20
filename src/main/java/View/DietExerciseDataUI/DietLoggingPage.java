package View.DietExerciseDataUI;
import Controller.DataLoggingHandler.DietLoggingController;
import View.MainUI.NavigateUI;
import View.ProfileUI.ProfileUIData;

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

    private ProfileUIData user;

    private Container c;
    private JLabel title;
    private JLabel date;
    private JTextField tdate;
    private JLabel meal;
    //private JTextField tmno;
    private JComboBox cmeal;
    private JLabel food1;
    private JComboBox food1c;
    private JLabel qty1;
    private JTextField qty1t;
    private JLabel food2;
    private JComboBox food2c;
    private JLabel qty2;
    private JTextField qty2t;
    private JLabel food3;
    private JComboBox food3c;
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
    private JTextArea result;
    private JLabel caloriesInfo;

    private String meals[]
            = {"breakfast", "lunch", "dinner", "snack"};

    private String food_options[] = {"",
            "Butter, unsalted" ,
            "Egg, chicken, whole, cooked, boiled in shell, hard-cooked" ,
            "Chicken, broiler, drumstick, meat and skin, flour coated, fried" ,
            "Chicken, broiler, breast, meat, stewed" ,
            "Avocado, raw, florida" ,
            "Pork, cured, back bacon, grilled" ,
            "Pork, cured, ham, whole, lean" ,
            "Broccoli, boiled, drained" ,
            "Carrot, boiled, drained" ,
            "Beef, ground, regular" ,
            "Fish, tuna, yellowfin, fresh, raw" ,
            "English muffin, whole wheat" ,
            "Sweets, jellies" ,
            "Dessert, pudding, banana, dry mix, instant, unprepared" ,
            "Candied foods, cherries" ,
            "Pasta, spaghetti, unenriched, cooked" ,
            "Cabbage, napa, cooked" ,
            "Candies, chocolate covered, dietetic or low calorie" ,
            "Sausage, Italian, turkey, smoked" ,
            "Zucchini, battered and fried" ,
            "Yogourt, vanilla flavoured, low fat (0.5-1.9% M.F.)" ,
            "Bread, whole grain (whole-wheat), commercial" ,
            "Apple, Fuji, raw, with skin"};


    public static void launch(ProfileUIData user){
        DietLoggingPage dlp = new DietLoggingPage(user);
        dlp.setSize(1280,720);
        dlp.setVisible(true);
    }

    public DietLoggingPage(ProfileUIData user)
    {
        this.user = user;

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

        food1c = new JComboBox(food_options);
        food1c.setFont(new Font("Arial", Font.PLAIN, 13));
        food1c.setSize(340, 30);
        food1c.setLocation(250, 200);
        c.add(food1c);

        qty1 = new JLabel("Food1 Quantity(g):");
        qty1.setFont(new Font("Arial", Font.PLAIN, 20));
        qty1.setSize(180, 30);
        qty1.setLocation(650, 200);
        c.add(qty1);

        qty1t = new JTextField();
        qty1t.setFont(new Font("Arial", Font.PLAIN, 15));
        qty1t.setSize(190, 30);
        qty1t.setLocation(850, 200);
        c.add(qty1t);

        food2 = new JLabel("Food2 Name:");
        food2.setFont(new Font("Arial", Font.PLAIN, 20));
        food2.setSize(140, 30);
        food2.setLocation(100, 250);
        c.add(food2);

        food2c = new JComboBox(food_options);
        food2c.setFont(new Font("Arial", Font.PLAIN, 13));
        food2c.setSize(340, 30);
        food2c.setLocation(250, 250);
        c.add(food2c);

        qty2 = new JLabel("Food2 Quantity(g):");
        qty2.setFont(new Font("Arial", Font.PLAIN, 20));
        qty2.setSize(180, 30);
        qty2.setLocation(650, 250);
        c.add(qty2);

        qty2t = new JTextField();
        qty2t.setFont(new Font("Arial", Font.PLAIN, 15));
        qty2t.setSize(190, 30);
        qty2t.setLocation(850, 250);
        c.add(qty2t);

        food3 = new JLabel("Food3 Name:");
        food3.setFont(new Font("Arial", Font.PLAIN, 20));
        food3.setSize(140, 30);
        food3.setLocation(100, 300);
        c.add(food3);

        food3c = new JComboBox(food_options);
        food3c.setFont(new Font("Arial", Font.PLAIN, 13));
        food3c.setSize(340, 30);
        food3c.setLocation(250, 300);
        c.add(food3c);

        qty3 = new JLabel("Food3 Quantity(g):");
        qty3.setFont(new Font("Arial", Font.PLAIN, 20));
        qty3.setSize(180, 30);
        qty3.setLocation(650, 300);
        c.add(qty3);

        qty3t = new JTextField();
        qty3t.setFont(new Font("Arial", Font.PLAIN, 15));
        qty3t.setSize(190, 30);
        qty3t.setLocation(850, 300);
        c.add(qty3t);

        term = new JCheckBox("Accept Terms And Conditions.");
        term.setFont(new Font("Arial", Font.PLAIN, 15));
        term.setSize(250, 20);
        term.setLocation(150, 350);
        c.add(term);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 380);
        sub.setActionCommand("submit");
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 380);
        reset.setActionCommand("reset");
        reset.addActionListener(this);
        c.add(reset);

        resultHeading = new JLabel("Your nutrients intake for this meal:");
        resultHeading.setFont(new Font("Arial", Font.BOLD, 18));
        resultHeading.setForeground(Color.GREEN);
        resultHeading.setSize(600, 30);
        resultHeading.setLocation(100, 420);
        resultHeading.setVisible(true);
        c.add(resultHeading);

        result = new JTextArea("");
        result.setFont(new Font("Arial", Font.ITALIC, 15));
        result.setForeground(Color.GREEN);
        result.setSize(850, 150);
        result.setLocation(100, 450);
        result.setEditable(false);

        JScrollPane scroll = new JScrollPane (result);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBounds(100, 450, 850, 150); // Set the bounds for the scroll pane, not the text area.
        //c.add(result);
        c.add(scroll);

        caloriesInfo = new JLabel("");
        caloriesInfo.setFont(new Font("Arial", Font.BOLD, 16));
        caloriesInfo.setForeground(Color.ORANGE);
        caloriesInfo.setSize(600, 30);
        caloriesInfo.setLocation(100, 610);
        caloriesInfo.setVisible(true);
        c.add(caloriesInfo);

        JButton viewHistoryDiets = new JButton("View Diets History");
        viewHistoryDiets.setFont(new Font("Arial", Font.PLAIN, 15));
        viewHistoryDiets.setSize(180, 30);
        viewHistoryDiets.setLocation(300, 650);
        viewHistoryDiets.setActionCommand("viewDietsHistory");
        viewHistoryDiets.addActionListener(this);
        c.add(viewHistoryDiets);

        JButton backButton = new JButton("Back");
        backButton.setBounds(500, 650, 180, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAction();
            }
        });

        add(backButton);

        setVisible(true);

    }

    public void backAction(){
        this.dispose();
        NavigateUI.launch(user);
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
            HashMap<String, Double> foods = new HashMap<>();
            String inputFood1 = food1c.getSelectedItem().toString();
            if (!inputFood1.isEmpty()){
                Double inputQty1 = Double.parseDouble(qty1t.getText());
                foods.put(inputFood1, inputQty1);
            }
            String inputFood2 = food2c.getSelectedItem().toString();
            if (!inputFood2.isEmpty()){
                Double inputQty2 = Double.parseDouble(qty2t.getText());
                foods.put(inputFood2, inputQty2);
            }
            String inputFood3 = food3c.getSelectedItem().toString();
            if (!inputFood3.isEmpty()){
                Double inputQty3 = Double.parseDouble(qty3t.getText());
                foods.put(inputFood3, inputQty3);
            }
            DietLoggingController c = DietLoggingController.getInstance();
            String[] nutrientsDataPack= c.logDiet(user.getId(), inputDate,inputMeal,foods);
            String nutrientInfo = nutrientsDataPack[0];
            String calories = nutrientsDataPack[1];
            caloriesInfo.setText("Calories: " + calories);
            resultHeading.setVisible(true);
            result.setText(nutrientInfo);
            //result.setText("<html>" + nutrientInfo.replaceAll("\n", "<br>"));
        } else if (comm.equals("viewDietsHistory")) {
            DietJournalPage.launch(user.getId());
        }
    }





}

