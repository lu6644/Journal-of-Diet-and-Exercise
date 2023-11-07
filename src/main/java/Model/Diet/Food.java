package Model.Diet;

import Model.DatabaseInteraction.FoodDAO;

import java.util.Objects;

public class Food {
    int id;
    String name;

    public Food(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Food(String name){
        this.name = name;
        this.id = queryId(name);
    }

    public int queryId(String name){
        FoodDAO foodDAO =FoodDAO.getInstance();
        return foodDAO.queryId(name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return id == food.id && Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
