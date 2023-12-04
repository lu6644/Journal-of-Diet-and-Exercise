package View.DietExerciseDataUI;

import java.util.Date;
import java.util.Map;

public class DietLoggingData {
    private int accountId;
    private Date date;
    private String meal;
    private Map<String, Double> foods;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Map<String, Double> getFoods() {
        return foods;
    }

    public void setFoods(Map<String, Double> foods) {
        this.foods = foods;
    }
}
