package Model.DatabaseInteraction;

import Model.Exercise.Exercise;

import java.sql.*;


public class ExerciseDAO {
    private Connection con = null;

    private static ExerciseDAO instance = null;

    private ExerciseDAO() {
        try {
            con = DatabaseConn.getDatabaseConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ExerciseDAO getInstance() {
        if (instance == null) {
            instance = new ExerciseDAO();
        }
        return instance;
    }

    public void addExerceise(Exercise exercise) {

    }

    public int searchExercises(int id){
        String sql = "select * from fitnessjournal.exercise_log where account_id = ?;";
        int counter = 0;
        try{
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs = p.executeQuery();

            while(rs.next()){
                counter++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return counter;
    }

    public void insertExercise(Exercise ex) {
        String insertSQL = "insert into fitnessjournal.exercise_log (account_id, exercise_id, exercise_date, exercise_type, intensity, duration) values (?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement p = con.prepareStatement(insertSQL);
            p.setInt(1, ex.getUser().getId());
            p.setInt(2, searchExercises(ex.getUser().getId())+1);
            p.setDate(3, ex.getDate());
            p.setString(4, ex.getExerciseType());
            p.setString(5, ex.getIntensity());
            p.setDouble(6,ex.getDuration());

            int affectedRows = p.executeUpdate();
            if(affectedRows == 0){
                System.out.println("Exercise insert failed");
            }
            else{
                System.out.println("Exercise insert successfully");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
