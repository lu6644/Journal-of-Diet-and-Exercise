package Model.DatabaseInteraction;

import Model.Diet.FoodCategory;
import Model.Diet.FoodCategoryFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class FoodDAO {
    private Connection con = null;
    private static FoodDAO instance = null;

    private FoodDAO(){
        try {
            con = DatabaseConnector.getConnection();
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

    //search food id give the food name
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

    //Return a map of food category and total amount intake of this category in double from the diet history
    public Map<FoodCategory, Double> queryFoodCategoryTotal(int profileId){
        Map<FoodCategory, Double> foodCategoryQuantity = new HashMap<>();
        String sql = "select CategoryName, sum(Quantity) as quantity from (select d.id, fd.food_id, " +
                "fn.Food_Description, fn.food_group_id, fg.FoodGroupName, fg.CategoryName, fd.Quantity, " +
                "fd.Quantity/fn.value_per_serving as value_CFG from fitnessjournal.diet d join " +
                "fitnessjournal.food_in_diet fd on d.id = fd.diet_id join fitnessjournal.food_name fn on " +
                "fd.food_id = fn.Food_ID join fitnessjournal.food_group fg on fn.food_group_id = " +
                "fg.FoodGroupID where d.account_id = ?) as foods group by CategoryName;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,profileId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String categoryStr = resultSet.getString("CategoryName");
                FoodCategory foodCategory = (new FoodCategoryFactory()).createFoodCategory(categoryStr);
                Double quantity = resultSet.getDouble("quantity");
                foodCategoryQuantity.put(foodCategory, quantity);
            }

            resultSet.close();
            preparedStatement.close();
            System.out.println(String.format("get food category summary success: "+foodCategoryQuantity));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foodCategoryQuantity;
    }

    //Return a map of food category and average daily number of CFG Servings of this category
    // from the diet history
    public Map<FoodCategory, Double> queryFoodCategoryDailyCFG(int profileId){
        Map<FoodCategory, Double> foodsCategoryDailyServings = new HashMap<>();
        String sql = "select CategoryName, sum(value_CFG)/count(distinct date) servings_daily_avg " +
                "from (select d.id, d.date, fd.food_id, fn.Food_Description, fn.food_group_id, " +
                "fg.FoodGroupName, fg.CategoryName, fd.Quantity, fd.Quantity/fn.value_per_serving " +
                "as value_CFG from fitnessjournal.diet d join fitnessjournal.food_in_diet fd on d.id = " +
                "fd.diet_id join fitnessjournal.food_name fn on fd.food_id = fn.Food_ID join " +
                "fitnessjournal.food_group fg on fn.food_group_id = fg.FoodGroupID where d.account_id = ?) " +
                "as foods group by CategoryName;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,profileId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String categoryStr = resultSet.getString("CategoryName");
                FoodCategory foodCategory = (new FoodCategoryFactory()).createFoodCategory(categoryStr);
                Double quantity = resultSet.getDouble("servings_daily_avg");
                foodsCategoryDailyServings.put(foodCategory, quantity);
            }

            resultSet.close();
            preparedStatement.close();
            System.out.println(String.format("get food category daily servings success: "+foodsCategoryDailyServings));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodsCategoryDailyServings;
    }

}
