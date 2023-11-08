package Model.DatabaseInteraction;

import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.Nutrient;

import java.sql.*;
import java.util.Map;

public class DietDAO {
    private Connection con = null;
    private static DietDAO instance = null;

    private DietDAO(){
        try {
            con = DatabaseConn.getDatabaseConn();
        }
        catch (Exception e) {
            // Handle the exception or log it as needed
            e.printStackTrace();
        }
    }

    public static DietDAO getInstance(){
        if (instance == null){
            instance = new DietDAO();
        }
        return instance;
    }

    public void addDiet2Db(Diet diet){
        insertDiet(diet);
        insertFoodsInDiet(diet);
        insertNutrientsInDiet(diet);

    }

    public void insertDiet(Diet diet){
        String stmt1 = "insert into fitnessjournal.diet(account_id,date,mealtype) values(?,?,?)";
        try {
            PreparedStatement p1 = con.prepareStatement(stmt1, Statement.RETURN_GENERATED_KEYS) ;
            p1.setInt(1, diet.getAccountId());
            p1.setDate(2, new Date(diet.getDate().getTime()));
            p1.setString(3,diet.getMeal().name());
            int affectedRows = p1.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = p1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                     diet.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFoodsInDiet(Diet diet){
        String stmt1 = "insert into fitnessjournal.food_in_diet(diet_id,food_id,Quantity) values(?,?,?)";

        try {
            con.setAutoCommit(false);
            PreparedStatement p1 = con.prepareStatement(stmt1);
            int dietID = diet.getId();
            for (Map.Entry<Food,Double> entry: diet.getIngredients().entrySet()) {
                Food food = entry.getKey();
                Double quantity = entry.getValue();
                p1.setInt(1, dietID);
                p1.setInt(2, food.getId());
                p1.setDouble(3, quantity);
                p1.addBatch();
            }
            p1.executeBatch();
            con.commit();

            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertNutrientsInDiet(Diet diet){
        String stmt1 = "insert into fitnessjournal.nutrient_in_diet(Diet_ID,Nutrient_ID,Value) values(?,?,?)";

        try {
            con.setAutoCommit(false);
            PreparedStatement p1 = con.prepareStatement(stmt1);
            int dietID = diet.getId();
            for (Map.Entry<Nutrient,Double> entry: diet.getNutrientsValue().entrySet()) {
                Nutrient nutrient = entry.getKey();
                Double value = entry.getValue();
                p1.setInt(1, dietID);
                p1.setInt(2, nutrient.getId());
                p1.setDouble(3, value);
                p1.addBatch();
            }
            p1.executeBatch();
            con.commit();

            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
