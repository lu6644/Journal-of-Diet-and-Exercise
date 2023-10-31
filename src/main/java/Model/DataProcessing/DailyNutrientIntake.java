package Model.DataProcessing;

public class DailyNutrientIntake {
	private int day;
	private double protein;
	private double carbohydrates;
	private double otherNutrients;

	public DailyNutrientIntake(int day, double protein, double carbohydrates, double otherNutrients) {
		this.day = day;
		this.protein = protein;
		this.carbohydrates = carbohydrates;
		this.otherNutrients = otherNutrients;
	}

	public double getDays() {
		return day;
	}

	public double getProtein() {
		return protein;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public double getOtherNutrients() {
		return otherNutrients;
	}
}
