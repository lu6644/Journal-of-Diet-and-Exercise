package Model.Diet;

public class FoodCategoryFactory {
    public FoodCategory createFoodCategory(String category)
    {
        switch (category) {
            case "Meat and Alternatives":
                return new Meat();
            case "Grain Products":
                return new Grain();
            case "Milk and Alternatives":
                return new Milk();
            case "Vegetables and Fruit":
                return new VegetableAndFruit();
            default:
                throw new IllegalArgumentException("Unknown food category "+category);
        }
    }
}
