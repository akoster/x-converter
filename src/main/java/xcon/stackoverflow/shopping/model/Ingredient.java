package xcon.stackoverflow.shopping.model;

import lombok.Data;

@Data
public class Ingredient {
    private Long id;
    private FoodItem foodItem;
    private Long weight;
}
