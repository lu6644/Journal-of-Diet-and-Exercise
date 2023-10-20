package Model.Diet;

import java.util.Objects;

public class Nutrient {
    int id;
    String name;
    String unit;


    public Nutrient(int id, String name, String unit){
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrient nutrient = (Nutrient) o;
        return id == nutrient.id && Objects.equals(name, nutrient.name) && Objects.equals(unit, nutrient.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unit);
    }
}
