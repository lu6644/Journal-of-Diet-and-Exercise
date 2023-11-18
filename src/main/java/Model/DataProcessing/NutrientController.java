package Model.DataProcessing;

import java.sql.Date;
import java.util.List;

/**
 * Controller class for managing nutrient data.
 */
public class NutrientController {
    private NutrientIntakeModel model;

    /**
     * Constructor for NutrientController.
     * @param model The NutrientIntakeModel instance to interact with data.
     */
    public NutrientController(NutrientIntakeModel model) {
        this.model = model;
    }

    /**
     * Retrieves nutrient intake data for visualization.
     * @param startDate The start date of the data retrieval period.
     * @param endDate The end date of the data retrieval period.
     * @return A list of NutrientIntake objects representing the nutrient data.
     */
    public List<NutrientIntake> getNutrientDataForVisualization(Date startDate, Date endDate) {
        return model.getNutrientIntakeForPeriod(startDate, endDate);
    }
    
    /**
     * Calculates the average nutrients for a given period.
     * @param startDate The start date of the period for calculation.
     * @param endDate The end date of the period for calculation.
     * @return NutrientAverages object containing average values of nutrients.
     */
    public NutrientAverages calculateAverageNutrients(Date startDate, Date endDate) {
        List<NutrientIntake> intakes = model.getNutrientIntakeForPeriod(startDate, endDate);
        double totalProtein = 0, totalCarbs = 0, totalFats = 0;
        int days = intakes.size();

        // Summing up nutrient values across all intakes
        for (NutrientIntake intake : intakes) {
            totalProtein += intake.getProtein();
            totalCarbs += intake.getCarbohydrates();
            totalFats += intake.getFats();
        }

        // Calculating averages for each nutrient
        return new NutrientAverages(totalProtein / days, totalCarbs / days, totalFats / days);
    }
    
}
