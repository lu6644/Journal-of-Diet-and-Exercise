package Model.DatabaseInteraction;

import Model.Diet.Diet;
import Model.Diet.Food;
import Model.Diet.MealType;
import Model.Diet.Nutrient;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
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
                throw new SQLException("Creating diet failed, no rows affected.");
            }
            try (ResultSet generatedKeys = p1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                     diet.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating diet failed, no ID obtained.");
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

    public List<Diet> queyDietsHistory(int profileId){
        List<Diet> dietsHistory= new LinkedList<Diet>();
        int caloryId = 208;
        String sql = "SELECT d.id, d.account_id, d.date, d.mealtype, nd.Nutrient_ID, nd.Value FROM fitnessjournal.diet d " +
                "join fitnessjournal.nutrient_in_diet nd on d.id = nd.Diet_ID where nd.Nutrient_ID=? and d.account_id=? " +
                "order by d.date desc;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, caloryId);
            preparedStatement.setInt(2, profileId);
            ResultSet r = preparedStatement.executeQuery();
            while (r.next()){
                int dietId = r.getInt("id");
                int accountId = r.getInt("account_id");
                Date dateSQL = r.getDate("date");
                java.util.Date date = new java.util.Date(dateSQL.getTime());
                String mealTypeStr = r.getString("mealtype");
                MealType mealType = MealType.valueOf(mealTypeStr);
                Double value = r.getDouble("Value");
                Diet diet = new Diet(accountId,date,mealType);
                diet.setId(dietId);
                diet.setCalories(value);
                System.out.println("Diet Info:\t"+diet.toString());
                dietsHistory.add(diet);
            }
            System.out.println("\n");

        } catch (SQLException e){
            e.printStackTrace();
        }
        return dietsHistory;
    }

    public Diet queryDietNutrientsDetail(int dietId){
        Diet diet = null;
        String sql = "SELECT d.id, d.account_id, d.date, d.mealtype, nn.Nutrient_ID, nn.Nutrient_Name, nn.Nutrient_Unit, nd.Value FROM " +
                "fitnessjournal.diet d join fitnessjournal.nutrient_in_diet nd on d.id = nd.Diet_ID join " +
                "fitnessjournal.nutrient_name nn on nd.Nutrient_ID = nn.Nutrient_ID where d.id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, dietId);
            ResultSet r = preparedStatement.executeQuery();
            while (r.next()){
                int accountId = r.getInt("account_id");
                Date dateSQL = r.getDate("date");
                java.util.Date date = new java.util.Date(dateSQL.getTime());
                String mealTypeStr = r.getString("mealtype");
                MealType mealType = MealType.valueOf(mealTypeStr);
                int nutrientID = r.getInt("Nutrient_ID");
                String nutrientName = r.getString("Nutrient_Name");
                String nutrientUnit = r.getString("Nutrient_Unit");
                Double value = r.getDouble("Value");
                if (diet==null) {
                    diet = new Diet(accountId,date,mealType);
                    diet.setId(dietId);
                }
                Nutrient nutrient = new Nutrient(nutrientID, nutrientName, nutrientUnit);
                diet.addToNutrient(nutrient,value);
                System.out.println("Nutrient Info:\t"+nutrient.toString()+"\tValue="+value);
            }
            diet.findCalories();
            System.out.println("\n");

        } catch (SQLException e){
            e.printStackTrace();
        }
        return diet;
    }
}
