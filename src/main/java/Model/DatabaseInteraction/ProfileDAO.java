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
        String sql2 = "insert into fitnessJournal.account values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            p2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profileId;

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


}
