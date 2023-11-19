package Model.Diet;

public abstract class FoodCategory {
    String name;

    public FoodCategory(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
