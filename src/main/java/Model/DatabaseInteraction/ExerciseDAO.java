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

    public void insertExercise(Exercise ex) {
        String insertSQL = "insert into fitnessjournal.exercise_log (account_id, exercise_id, exercise_date, exercise_type, intensity, duration) values (?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement p1 = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/fitnessjournal?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "Wuhuan0515.";

    public static void main(String args[]){
        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("连接数据库。。。");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println(("....."));
            stmt = conn.createStatement();
            String sql;
            sql = "select * from fitnessJournal.exercise";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                System.out.println("exercise_type: "+ rs.getString("exercise_type"));
                System.out.println("intensity: "+ rs.getString("intensity"));
                System.out.println("met_valiue: " + rs.getString("met_value"));
                System.out.println("\n");
            }
            rs.close();
            stmt.close();
            conn.close();



        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
