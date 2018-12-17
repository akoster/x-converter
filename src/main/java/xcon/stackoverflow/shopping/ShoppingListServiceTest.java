package xcon.stackoverflow.shopping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import xcon.stackoverflow.shopping.exception.ShoppingServiceException;
import xcon.stackoverflow.shopping.model.*;
import xcon.stackoverflow.shopping.service.DietMealsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceTest {

    @InjectMocks
    private ShoppingListService instanceUnderTest;

    @Mock
    private DietMealsService dietMealsService;
    @Mock
    private User user;
    @Mock
    private Diet diet;
    @Mock
    private Meal meal;

    @Test(expected = ShoppingServiceException.class)
    public void testCreateShoppingListUserDietNull() {
        // SETUP
        User user = mock(User.class);
        when(user.getDiet()).thenReturn(null);

        // CAlL
        instanceUnderTest.createShoppingList(user);
    }

    @Test(expected = ShoppingServiceException.class)
    public void testCreateShoppingListUserDietMealsNull() {
        // SETUP
        when(user.getDiet()).thenReturn(diet);
        when(diet.getMeals()).thenReturn(null);

        // CAlL
        instanceUnderTest.createShoppingList(user);
    }

    @Test(expected = ShoppingServiceException.class)
    public void testCreateShoppingListUserDietMealsEmpty() {
        // SETUP
        when(user.getDiet()).thenReturn(diet);
        List<Meal> meals = new ArrayList<>();
        when(diet.getMeals()).thenReturn(meals);

        // CAlL
        instanceUnderTest.createShoppingList(user);
    }


    @Test
    public void testCreateShoppingListAdjustsIngredients() {
        // SETUP
        when(user.getDiet()).thenReturn(diet);
        List<Meal> meals = Collections.singletonList(meal);
        when(diet.getMeals()).thenReturn(meals);

        // CAlL
        instanceUnderTest.createShoppingList(user);

        // VERIFY
        verify(dietMealsService).adjustIngredients(meals);
    }

    @Test
    public void testCreateShoppingListAddsWeights() {
        // SETUP
        when(user.getDiet()).thenReturn(diet);
        when(diet.getMeals()).thenReturn(Collections.singletonList(meal));
        Recipe recipe = mock(Recipe.class);
        when(meal.getRecipe()).thenReturn(recipe);
        Ingredient ingredient1 = mock(Ingredient.class);
        Ingredient ingredient2 = mock(Ingredient.class);
        when(recipe.getIngredients()).thenReturn(Arrays.asList(ingredient1, ingredient2));
        FoodItem foodItem = mock(FoodItem.class);
        when(ingredient1.getFoodItem()).thenReturn(foodItem);
        when(ingredient2.getFoodItem()).thenReturn(foodItem);
        Long weight1 = 42L;
        Long weight2 = 1337L;
        when(ingredient1.getWeight()).thenReturn(weight1);
        when(ingredient2.getWeight()).thenReturn(weight2);

        // CAlL
        ShoppingList shoppingList = instanceUnderTest.createShoppingList(user);

        // VERIFY
        Long expectedWeight = weight1 + weight2;
        Long actualWeight = shoppingList.getIngredientWeights().get(foodItem);
        assertEquals(expectedWeight, actualWeight);
    }
}

