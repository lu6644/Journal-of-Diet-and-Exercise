package Model.DatabaseInteraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FoodDAO {
    private Connection con = null;
    private static FoodDAO instance = null;

    private FoodDAO(){
        try {
            con = DatabaseConn.getDatabaseConn();
        }
        catch (Exception e) {
            // Handle the exception or log it as needed
            e.printStackTrace();
        }
    }

    public static FoodDAO getInstance(){
        if (instance == null){
            instance = new FoodDAO();
        }
        return instance;
    }

    public int queryId(String name){
        int foodId = -1; // Default value if not found
        try {
            String sql = "SELECT food_id FROM fitnessjournal.food_name where Food_Description = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                foodId = resultSet.getInt("Food_Id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Query Food Name %s Result: id=%d",name,foodId));
        return foodId;
    }

}
