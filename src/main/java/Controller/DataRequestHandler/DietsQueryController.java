package Controller.DataRequestHandler;

import Model.DatabaseInteraction.DietDAO;
import Model.Diet.Diet;
import Model.Diet.Nutrient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DietsQueryController {
    private static DietsQueryController instance = null;

    private DietsQueryController() {

    }

    public static DietsQueryController getInstance() {
        if (instance == null) {
            instance = new DietsQueryController();
        }
        return instance;
    }

    //get all diets of a user
    public List<Map<String,String>> requestDietsHistory(int profileId){
        List<Diet> dietsHistory = DietDAO.getInstance().queyDietsHistory(profileId);
        return DietsHistoryUIMapper(dietsHistory);
    }

    //get nutrients breakdown for a given diet
    public Map<String,String> requestNutrientsDetail(int dietId){
        Diet diet = DietDAO.getInstance().queryDietNutrientsDetail(dietId);
        Map<Nutrient,Double> nutrientsValue = diet.getNutrientsValue();
        return NutrientsUIMapper(nutrientsValue);
    }

    //Map the database returned value to value that can be easily displayed in frontend
    private List<Map<String,String>> DietsHistoryUIMapper(List<Diet> diets){
        return diets.stream().map(diet -> Map.of(
                "id",String.valueOf(diet.getId()),
                "date", Date2Str(diet.getDate()),
                "mealType", diet.getMeal().name(),
                "calories", diet.getCalories()+"kCal"
                )).collect(Collectors.toList());
    }

    //Date to string
    private String Date2Str(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    //Map the database returned value to value that can be easily displayed in frontend
    private Map<String,String> NutrientsUIMapper(Map<Nutrient, Double> nutrientsValue){
        return nutrientsValue.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey().getName(),
                entry -> entry.getValue() + entry.getKey().getUnit()));
    }
}
