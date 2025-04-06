package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;

public class RecipeBookTest {

    RecipeBook recipeBook;
    Recipe recipe1;
    Recipe recipe2;

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();
        recipe1 = new Recipe();
        recipe2 = new Recipe();
        
        recipe1.setName("Coffee");
        recipe2.setName("Tea");
    }

    @Test
    @DisplayName("Test adding a recipe successfully and preventing duplicates")
    void testAddRecipe() {  
        assertAll(
                () -> assertTrue(recipeBook.addRecipe(recipe1), "Recipe should be added successfully"),
                () -> assertFalse(recipeBook.addRecipe(recipe1), "Duplicate recipe shouldn't be added")
            );
    }

    @Test
    @DisplayName("Test retrieving recipes from the recipe book")
    void testGetRecipes() {
        recipeBook.addRecipe(recipe1);
        Recipe[] recipes = recipeBook.getRecipes();
        assertNotNull(recipes, "Recipe array shouldn't be null");
        assertNotNull(recipes[0], "First recipe slot shouldn't be null after adding a recipe");
    }

    @Test
    @DisplayName("Test deleting an existing recipe")
    void testDeleteRecipe() {
        recipeBook.addRecipe(recipe1);
        String deletedRecipeName = recipeBook.deleteRecipe(0);

        assertEquals("Coffee",deletedRecipeName, "Should return the deleted recipe's name");
        assertNotNull(recipeBook.getRecipes()[0], "After deletion, slot should contain a new Recipe object");
    }

    @Test
    @DisplayName("Test editing an existing recipe")
    void testEditRecipe() {
        recipeBook.addRecipe(recipe1);
        
        String oldRecipeName = recipeBook.editRecipe(0, recipe2);
        
        assertEquals("Coffee", oldRecipeName, "Should return the old recipe's name");
        assertEquals("", recipeBook.getRecipes()[0].getName(), "Recipe name should be empty after edit");
    }
    
    @Test
    @DisplayName("Test adding recipes beyond the maximum allowed")
    void testAddRecipeBeyondLimit() {
        for (int i = 0; i < 4; i++) {
            Recipe recipe = new Recipe();
            recipe.setName("Recipe " + i);
            recipeBook.addRecipe(recipe);
        }
        Recipe extraRecipe = new Recipe();
        extraRecipe.setName("Recipe 4");
        assertFalse(recipeBook.addRecipe(extraRecipe), "Cannot add more than the maximum number of recipes");
    }
 
    @ParameterizedTest
    @ValueSource(strings = { "Recipe Coffee", "Recipe Tea" })
    @DisplayName("Test Adding Recipes With Parameterized Inputs")
    void testAddRecipeWithParameterizedInputs(String name) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(name);
        assertTrue(recipeBook.addRecipe(newRecipe));
    }
    
    @Test
    @Timeout(1)
    @DisplayName("Test RecipeBookTimeout")
    void testTimeout() {
    	recipeBook.addRecipe(recipe1);
    	recipeBook.addRecipe(recipe2);
    }
}

