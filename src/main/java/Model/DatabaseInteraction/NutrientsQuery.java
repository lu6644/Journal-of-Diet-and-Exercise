package Model.DatabaseInteraction;

import Model.Diet.Food;
import Model.Diet.Nutrient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NutrientsQuery {
    //Return a map of nutrient and value per 100g of food
    public static Map<Nutrient,Double> extractNutrients(Food food){
        //TODO: Below are hardcoded test data. Implement querying from Database Interaction Module after DB is setup
        Map<Nutrient,Double> nutrientsUnitValue = new HashMap<Nutrient,Double>();
        Nutrient n1 = new Nutrient(1,"nutrient1","g");
        Nutrient n2 = new Nutrient(2,"nutrient2","mg");
        Nutrient n3 = new Nutrient(3,"nutrient3","µg");
        Nutrient n4 = new Nutrient(4,"nutrient4","g");
        switch (food.getName()){
            case "f1":
                nutrientsUnitValue.put(n1,2.2);
                nutrientsUnitValue.put(n3,700.0);
                break;
            case "f2":
                nutrientsUnitValue.put(n1, 1.2);
                nutrientsUnitValue.put(n2,220.0);
                nutrientsUnitValue.put(n4,2.6);
                break;
            case "f3":
                nutrientsUnitValue.put(n3,600.0);
                break;
        }
        return nutrientsUnitValue;
    }
}
