package xcon.stackoverflow.shopping.model;

import lombok.Data;

@Data
public class FoodItem {
    private Long id;
    private String name;
    private Float caloriesPer100g;
    private Float proteinPer100g;
    private Float carbohydratePer100g;
    private Float fatPer100g;
}
