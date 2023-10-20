<<<<<<< HEAD
package Model.Diet;

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
        //TODO: get food id from database given food name
        return 0;
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
}
=======
package Model.Diet;

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
        //TODO: get food id from database given food name
        return 0;
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
}
>>>>>>> 93bedb21d24518c10490518d2da9843fa27e8a46
