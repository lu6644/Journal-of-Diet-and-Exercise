package Model.DataProcessing;

import java.sql.Date;
import java.util.List;

public class NutrientController {
    private NutrientIntakeModel model;

    public NutrientController(NutrientIntakeModel model) {
        this.model = model;
    }

    public List<NutrientIntake> getNutrientDataForVisualization(Date startDate, Date endDate) {
        return model.getNutrientIntakeForPeriod(startDate, endDate);
    }
    
    public NutrientAverages calculateAverageNutrients(Date startDate, Date endDate) {
        List<NutrientIntake> intakes = model.getNutrientIntakeForPeriod(startDate, endDate);
        double totalProtein = 0, totalCarbs = 0, totalFats = 0;
        int days = intakes.size();

        for (NutrientIntake intake : intakes) {
            totalProtein += intake.getProtein();
            totalCarbs += intake.getCarbohydrates();
            totalFats += intake.getFats();
        }

        return new NutrientAverages(totalProtein / days, totalCarbs / days, totalFats / days);
    }
    
}

