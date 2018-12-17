package xcon.stackoverflow.shopping;

import lombok.Data;
import xcon.stackoverflow.shopping.model.Ingredient;
import xcon.stackoverflow.shopping.model.FoodItem;

import java.util.HashMap;
import java.util.Map;

@Data
public class ShoppingList {

    private final Map<FoodItem, Long> ingredientWeights = new HashMap<>();

    public void addWeight(Ingredient ingredient) {
        FoodItem foodItem = ingredient.getFoodItem();
        Long weight = ingredientWeights.getOrDefault(foodItem, 0L);
        weight += ingredient.getWeight();
        ingredientWeights.put(foodItem, weight);
    }
}
