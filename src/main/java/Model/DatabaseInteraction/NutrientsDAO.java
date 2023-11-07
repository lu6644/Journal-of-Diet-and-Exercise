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
		// TODO: Below are hardcoded test data. Implement querying from Database
		// Interaction Module after DB is setup
		Map<Nutrient, Double> nutrientsUnitValue = new HashMap<Nutrient, Double>();
//		Nutrient n1 = new Nutrient(1, "nutrient1", "g");
//		Nutrient n2 = new Nutrient(2, "nutrient2", "mg");
//		Nutrient n3 = new Nutrient(3, "nutrient3", "µg");
//		Nutrient n4 = new Nutrient(4, "nutrient4", "g");
//		switch (food.getName()) {
//		case "f1":
//			nutrientsUnitValue.put(n1, 2.2);
//			nutrientsUnitValue.put(n3, 700.0);
//			break;
//		case "f2":
//			nutrientsUnitValue.put(n1, 1.2);
//			nutrientsUnitValue.put(n2, 220.0);
//			nutrientsUnitValue.put(n4, 2.6);
//			break;
//		case "f3":
//			nutrientsUnitValue.put(n3, 600.0);
//			break;
//		}

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
				System.out.println(nutrient.toString() + "\n");
			}
			System.out.println("\n");
		} catch (SQLException e){
			e.printStackTrace();
		}

		return nutrientsUnitValue;
	}
}
