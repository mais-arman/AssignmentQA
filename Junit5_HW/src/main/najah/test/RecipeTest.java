package main.najah.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Recipe;
import main.najah.code.RecipeException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

@DisplayName("Recipe Tests")
public class RecipeTest {

	Recipe recipe;

    @BeforeEach
	void setUp() throws Exception {
		
    	recipe = new Recipe();
	}
	
    @Test
    @DisplayName("Test default values of a new Recipe for the coffee maker")
    void testDefaultValues() {
        assertAll(
            () -> assertEquals("", recipe.getName()),
            () -> assertEquals(0, recipe.getPrice()),
            () -> assertEquals(0, recipe.getAmtCoffee()),
            () -> assertEquals(0, recipe.getAmtMilk()),
            () -> assertEquals(0, recipe.getAmtSugar()),
            () -> assertEquals(0, recipe.getAmtChocolate())
        );
    }

    @Test
    @DisplayName("Test setting valid input")
    void testSetValidInput() throws RecipeException {
        recipe.setAmtChocolate("0");
        recipe.setAmtCoffee("1");
        recipe.setAmtMilk("2");
        recipe.setAmtSugar("3");

        assertAll(
            () -> assertEquals(0, recipe.getAmtChocolate()),
            () -> assertEquals(1, recipe.getAmtCoffee()),
            () -> assertEquals(2, recipe.getAmtMilk()),
            () -> assertEquals(3, recipe.getAmtSugar())
        );
    }

    @Test
    @DisplayName("Test setting invalid input")
    void testSetInvalidInput() {
        assertAll(
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtChocolate("-1")),
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtCoffee("-2")),
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtMilk("-3")),
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtSugar("-4"))
        );
        
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2"})
    @DisplayName("Parameterized Test")
    void testValidPrices(String price) throws RecipeException {
        recipe.setPrice(price);
        assertEquals(Integer.parseInt(price), recipe.getPrice());
    }


    @Test
    @DisplayName("Test toString returns the name of the recipe")
    void testToString() {
        recipe.setName("Mocha");
        assertEquals("Mocha", recipe.toString());
    }

    @Test
    @DisplayName("Test equals and hashCode")
    void testEqualsAndHashCode() {
        Recipe r1 = new Recipe();
        r1.setName("Espresso");

        Recipe r2 = new Recipe();
        r2.setName("Espresso");

        Recipe r3 = new Recipe();
        r3.setName("Mocha");

        assertAll("Equality and hashCode",
            () -> assertEquals(r1, r2),
            () -> assertEquals(r1.hashCode(), r2.hashCode()),
            () -> assertNotEquals(r1, r3)
        );
    }

    
    @Test
    @DisplayName("Test equals() Method")
    void testEqualsMethod() {
        Recipe r1 = new Recipe();
        r1.setName("Espresso");

        Recipe r2 = new Recipe();
        r2.setName("Espresso");

        Recipe r3 = new Recipe();
        r3.setName("Mocha");

        Recipe r4 = new Recipe();
        r4.setName(null);

        Recipe r5 = new Recipe();
        r5.setName(null);

        assertAll(
            () -> assertTrue(r1.equals(r1)),
            () -> assertFalse(r1.equals(null)),
            () -> assertTrue(r1.equals(r2)),
            () -> assertFalse(r1.equals(r3)),
            () -> assertTrue(r4.equals(r5)),
            () -> assertFalse(r1.equals(r4)),
            () -> assertFalse(r4.equals(r1))
        );
    }

    @Test
    @DisplayName("Test exception when coffee is negative")
    void testCoffeeNegative() {
        RecipeException exception = assertThrows(RecipeException.class, () -> recipe.setAmtCoffee("-2"));
        assertEquals("Units of coffee must be a positive integer", exception.getMessage());
    }

    @Test
    @DisplayName("Test exception when milk is negative")
    void testMilkNegative() {
        RecipeException exception = assertThrows(RecipeException.class, () -> recipe.setAmtMilk("-3"));
        assertEquals("Units of milk must be a positive integer", exception.getMessage());
    }

    @Test
    @DisplayName("Test exception when sugar is negative")
    void testSugarNegative() {
        RecipeException exception = assertThrows(RecipeException.class, () -> recipe.setAmtSugar("-1"));
        assertEquals("Units of sugar must be a positive integer", exception.getMessage());
    }

    @Test
    @DisplayName("Test exception when price is negative")
    void testPriceNegative() {
        RecipeException exception = assertThrows(RecipeException.class, () -> recipe.setPrice("-10"));
        assertEquals("Price must be a positive integer", exception.getMessage());
    }

    @Test
    @DisplayName("Test exception when price is non-numeric")
    void testPriceNonNumericInput() {
        RecipeException exception = assertThrows(RecipeException.class, () -> recipe.setPrice("abc"));
        assertEquals("Price must be a positive integer", exception.getMessage());
    }
    
    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @DisplayName("Timeout Test")
    void testSetValuesTimeout() throws RecipeException {
        recipe.setAmtCoffee("3");
        recipe.setAmtMilk("1");
        recipe.setAmtSugar("1");
        recipe.setAmtChocolate("0");
        recipe.setPrice("7");
    }

}
