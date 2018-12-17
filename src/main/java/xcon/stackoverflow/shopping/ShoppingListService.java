package xcon.stackoverflow.shopping;

import xcon.stackoverflow.shopping.exception.ShoppingServiceException;
import xcon.stackoverflow.shopping.model.*;
import xcon.stackoverflow.shopping.service.DietMealsService;

import java.util.List;

public class ShoppingListService {

    private DietMealsService dietMealsService;

    public ShoppingListService(DietMealsService dietMealsService) {
        this.dietMealsService = dietMealsService;
    }

    public ShoppingList createShoppingList(User user) {
        List<Meal> meals = getMeals(user);
        dietMealsService.adjustIngredients(meals);
        return createShoppingList(meals);
    }

    private List<Meal> getMeals(User user) {
        Diet diet = user.getDiet();
        if (diet == null || diet.getMeals() == null || diet.getMeals().isEmpty()) {
            throw new ShoppingServiceException("User doesn't have diet");
        }
        return diet.getMeals();
    }

    private ShoppingList createShoppingList(List<Meal> meals) {
        ShoppingList shoppingList = new ShoppingList();
        for (Meal meal : meals) {
            processIngredientWeights(meal.getRecipe().getIngredients(), shoppingList);
        }
        return shoppingList;
    }

    private void processIngredientWeights(List<Ingredient> ingredients, ShoppingList shoppingList) {
        for (Ingredient ingredient : ingredients) {
            shoppingList.addWeight(ingredient);
        }
    }

}
