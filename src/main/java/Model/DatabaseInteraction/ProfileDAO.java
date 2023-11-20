package Model.DatabaseInteraction;


import Model.Profile.UserProfile;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProfileDAO {
    private Connection con = null;
    private static ProfileDAO instance = null;

    private ProfileDAO(){
        try {
            con = DatabaseConn.getDatabaseConn();
        }
        catch (Exception e) {
            // Handle the exception or log it as needed
            e.printStackTrace();
        }
    }

    public static ProfileDAO getInstance(){
        if (instance == null){
            instance = new ProfileDAO();
        }
        return instance;
    }

    public int insertNewProfile(UserProfile profile){
        //insert user table
        int profileId = -1;
        String sql1 = "insert into fitnessJournal.user(username, password) values (?, ?);";
        try {
            PreparedStatement p1 = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS) ;
            p1.setString(1, profile.getUsername());
            p1.setString(2, profile.getPassword());
            int affectedRows = p1.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = p1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    profileId = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //insert account table
        String sql2 = "insert into fitnessJournal.account values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement p2 = con.prepareStatement(sql2) ;
            p2.setInt(1, profileId);
            p2.setString(2, profile.getFirstName());
            p2.setString(3, profile.getLastName());
            p2.setInt(4, profile.getAge());
            p2.setString(5,profile.getGender());
            p2.setDouble(6,profile.getHeight());
            p2.setDouble(7,profile.getWeight());
            p2.setString(8,profile.getSpecialPeriod());
            p2.setInt(9,profile.isHasWeightScale() ?1 : 0);
            p2.setString(10, profile.getHeightUnit());
            p2.setString(11, profile.getWeightUnit());
            p2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profileId;

    }


    public void updateProfile(int id, String firstname, String lastname, int age, String gender, Double height, String heightUnit, Double weight, String weightUnit, String specialPeriod, Boolean hasWeightScale){
        String sql = "update fitnessjournal.account set first_name = ?, last_name = ?, age = ?, gender = ?, height = ?, weight = ?, special_period = ?, has_weight_scale = ?, height_unit = ?, weight_unit = ? where account_id = ?;";
        try{
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, firstname);
            p.setString(2,lastname);
            p.setInt(3, age);
            p.setString(4,gender);
            p.setDouble(5, height);
            p.setDouble(6, weight);
            p.setString(7, specialPeriod);
            p.setInt(8, hasWeightScale? 1 : 0);
            p.setString(9, heightUnit);
            p.setString(10, weightUnit);
            p.setInt(11, id);

            int rowsAffected = p.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("User with ID: " + id + "updated succesfully.");
            }else{
                System.out.println("User with ID: "+ id +" not found or not updated.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserProfile> getProfileList(){
        List<UserProfile> profiles = new LinkedList<UserProfile>();
        String sql = "SELECT * FROM fitnessjournal.account;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int profileId = resultSet.getInt("account_id");
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                double height = resultSet.getDouble("height");
                double weight = resultSet.getDouble("weight");
                String specialPeriod = resultSet.getString("special_period");
                Boolean hasWeightScale = resultSet.getInt("has_weight_scale") == 1;
                UserProfile profile = new UserProfile(fName,lName,age,gender,height,weight,
                        specialPeriod,hasWeightScale);
                profile.setId(profileId);
                profiles.add(profile);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(String.format("get profile list success: "+profiles));
        return profiles;
    }

    public int getProfileID(String username){
        int profileID = 0;
        String sql = "select * from fitnessjournal.user where username = ?;";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                profileID = resultSet.getInt("account_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return profileID;
    }

    public UserProfile getProfile(int id){
        UserProfile user = new UserProfile();
        user.setId(id);
        String sql = "select * from fitnessjournal.account where account_id = ?;";
        try{
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();

            while(rs.next()){
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("Gender"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setHeightUnit(rs.getString("height_unit"));
                user.setWeightUnit(rs.getString("weight_unit"));
                user.setSpecialPeriod(rs.getString("special_period"));
                user.setHasWeightScale(rs.getInt("has_weight_scale") == 1? true:false);
                user.setHeightUnit(rs.getString("height_unit"));
                user.setWeightUnit(rs.getString("weight_unit"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public UserProfile getProfileByUsername(String username, String password){
        UserProfile user = new UserProfile();
        String sql = "select * from FitnessJournal.account a, FitnessJournal.user u\n" +
                "where u.account_id = a.account_id and username = ? and password = ?;";
        try{
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, username);
            p.setString(2, password);

            ResultSet rs = p.executeQuery();

            while(rs.next()){
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("Gender"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setHeightUnit(rs.getString("height_unit"));
                user.setWeightUnit(rs.getString("weight_unit"));
                user.setSpecialPeriod(rs.getString("special_period"));
                user.setHasWeightScale(rs.getInt("has_weight_scale") == 1? true:false);
                user.setHeightUnit(rs.getString("height_unit"));
                user.setWeightUnit(rs.getString("weight_unit"));
                user.setId(rs.getInt("account_id"));
                user.setUsername(username);
                user.setPassword(password);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public int verifyPassword(String username, String password){
        String sql = "select password from fitnessjournal.user where username = ?;";
        try{
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, username);

            ResultSet rs = p.executeQuery();
            String pw = "";
            while(rs.next()){
                pw = rs.getString("password");
            }
            if(pw.equals(password)){
                return 1;
            }
            else{
                return 0;
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
