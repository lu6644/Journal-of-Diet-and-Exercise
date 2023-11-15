package Controller.DataRequestHandler;

import Model.DatabaseInteraction.DietDAO;
import Model.Diet.Diet;

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

    public List<Map<String,String>> requestDietsHistory(int profileId){
        List<Diet> dietsHistory = DietDAO.getInstance().queyDietsHistory(profileId);
        return DietsHistoryUIMapper(dietsHistory);
    }

    private List<Map<String,String>> DietsHistoryUIMapper(List<Diet> diets){
        return diets.stream().map(diet -> Map.of(
                "id",String.valueOf(diet.getId()),
                "date", Date2Str(diet.getDate()),
                "mealType", diet.getMeal().name(),
                "calories", diet.getCalories()+"kCal"
                )).collect(Collectors.toList());
    }

    private String Date2Str(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
