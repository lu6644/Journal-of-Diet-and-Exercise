package Model.DatabaseInteraction;

import Model.Exercise.Exercise;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ExerciseDAO {
    private Connection con = null;

    private static ExerciseDAO instance = null;

    // Private constructor to initialize the database connection
    private ExerciseDAO() {
        try {
            con = DatabaseConnector.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Singleton pattern: getInstance method to get the instance of ExerciseDAO
    public static ExerciseDAO getInstance() {
        if (instance == null) {
            instance = new ExerciseDAO();
        }
        return instance;
    }

    // Count the number of exercises for a specified account ID
    public int searchExercises(int id) {
        String sql = "SELECT * FROM fitnessjournal.exercise_log WHERE account_id = ?;";
        int counter = 0;
        try {
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                counter++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return counter;
    }

    // Insert a new exercise into the database
    public Exercise insertExercise(Exercise ex) {
        String insertSQL = "INSERT INTO fitnessjournal.exercise_log (account_id, exercise_id, exercise_date, exercise_type, intensity, duration) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            int ex_id = searchExercises(ex.getAccoount_id()) + 1;
            PreparedStatement p = con.prepareStatement(insertSQL);
            p.setInt(1, ex.getAccoount_id());
            p.setInt(2, ex_id);
            p.setDate(3, ex.getDate());
            p.setString(4, ex.getExerciseType());
            p.setString(5, ex.getIntensity());
            p.setDouble(6, ex.getDuration());

            int affectedRows = p.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Exercise insert failed");
            } else {
                System.out.println("Exercise insert successfully");
            }

            ex.setExercise_id(ex_id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ex;
    }

    // Insert calories burnt information for a specific exercise
    public void insertExerciseCaloriesBurnt(int account_id, int exercise_id, double calories_burnt) {
        String sql = "INSERT INTO fitnessjournal.exercise_calories_burnt(exercise_id, account_id, calories_burnt) VALUES (?, ?, ?);";
        try {
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, exercise_id);
            p.setInt(2, account_id);
            p.setDouble(3, calories_burnt);

            int affectedRows = p.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Exercise calories burnt insert failed");
            } else {
                System.out.println("Exercise calories burnt insert successfully");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieve a list of exercises for a specified account ID
    public List<Exercise> getExercises(int id) {
        List<Exercise> exercises = new LinkedList<>();
        String sql = "SELECT * FROM fitnessjournal.exercise_log WHERE account_id = ?;";
        try {
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                int exercise_id = rs.getInt("exercise_id");
                Date exercise_date = rs.getDate("date");
                String exercise_type = rs.getString("exercise_type");
                String intensity = rs.getString("intensity");
                double duration = rs.getDouble("duration");

                Exercise ex = new Exercise(id, exercise_date, duration, intensity, exercise_type);
                ex.setExercise_id(exercise_id);

                exercises.add(ex);
            }

            rs.close();
            p.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(String.format("get exercises list success: " + exercises));
        return exercises;
    }
}
