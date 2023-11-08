package Model.Diet;

import View.DietExerciseDataUI.DietLoggingPage;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Diet diet = new Diet(1, new Date("2023-10-30"), MealType.DINNER);

    }
}
