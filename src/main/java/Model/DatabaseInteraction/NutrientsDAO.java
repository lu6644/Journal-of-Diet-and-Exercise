package Model.DatabaseInteraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Model.Diet.Food;
import Model.Diet.Nutrient;

public class NutrientsDAO {
	private Connection con = null;
	private static NutrientsDAO instance = null;

	private NutrientsDAO(){
		try {
			con = DatabaseConn.getDatabaseConn();
		}
		catch (Exception e) {
			// Handle the exception or log it as needed
			e.printStackTrace();
		}
	}

	public static NutrientsDAO getInstance(){
		if (instance == null){
			instance = new NutrientsDAO();
		}
		return instance;
	}
	// Return a map of nutrient and value per 100g of food
	public Map<Nutrient, Double> extractNutrients(Food food) {
		Map<Nutrient, Double> nutrientsUnitValue = new HashMap<Nutrient, Double>();

		String sql = "SELECT food_id, a.nutrient_id,n.Nutrient_Name, nutrient_value, nutrient_unit from " +
				"fitnessjournal.nutrient_amount a join fitnessjournal.nutrient_name n on a.Nutrient_ID = " +
				"n.Nutrient_ID where Food_ID = ?";
		System.out.println(String.format("nutrientsList for food %s: \n",food.toString()));
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, food.getId());
			ResultSet r = preparedStatement.executeQuery();
			while (r.next()){
				int nutrientId = r.getInt("nutrient_id");
				String nutrientName = r.getString("Nutrient_Name");
				Double valuePer100G = r.getDouble("nutrient_value");
				String nutrientUnit = r.getString("nutrient_unit");
				Nutrient nutrient = new Nutrient(nutrientId,nutrientName,nutrientUnit);
				nutrientsUnitValue.put(nutrient, valuePer100G);
				System.out.println(nutrient.toString() + "\tunitValue:" + valuePer100G + "\n");
			}
			System.out.println("\n");
		} catch (SQLException e){
			e.printStackTrace();
		}

		return nutrientsUnitValue;
	}
}
