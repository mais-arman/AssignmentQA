package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Product;

public class ProductTest {
    Product p;

	@BeforeEach
	void setUp() throws Exception {
		
		p = new Product("Laptop", 5000.0);
	}
	
    @Test
    @DisplayName("Product initialized with valid name, price, and default discount")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testProductInitialization() {
        assertAll(
            () -> assertEquals("Laptop", p.getName()),
            () -> assertEquals(5000.0, p.getPrice()),
            () -> assertEquals(0.0, p.getDiscount())
        );
    }
	 
    @Test
    @DisplayName("Default discount should be 0.0")
    void testDefaultDiscount() {
        assertEquals(0.0, p.getDiscount());
    }

	@Test
    @DisplayName("Valid discount should update discount and calculate correct final price")
    void testValidDiscount() {
        p.applyDiscount(10);
        assertEquals(10.0, p.getDiscount());
        assertEquals(4500.0, p.getFinalPrice(), 0.01);
    }


    @Test
    @DisplayName("Invalid discount (negative or over 50%) should throw an exception")
    void testInvalidDiscount() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(-20));
        assertEquals("Invalid discount", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(75));
        assertEquals("Invalid discount", exception2.getMessage());
    }
    
    
    @ParameterizedTest
    @DisplayName("Parameterized Test for Different Discount Values")
    @ValueSource(doubles = {10.0, 20.0, 30.0, 40.0})
    void testParameterizedDiscount(double discount) {
        p.applyDiscount(discount);
        double expectedFinalPrice = 5000.0 * (1 - discount / 100);
        assertAll(
            () -> assertEquals(discount, p.getDiscount()),
            () -> assertEquals(expectedFinalPrice, p.getFinalPrice(), 0.01)
        );
    }
    
    @Test
    @DisplayName("Product with a negative price should throw an exception")
    void testNegativePrice() {
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Product("Laptop", -5000.0));
        assertEquals("Price must be non-negative", exception.getMessage());
    }

    @Test
    @DisplayName("Product Price Without Discount")
    void testFinalPriceWithoutDiscount() {
        assertEquals(5000.0, p.getFinalPrice(), 0.01);
    }

}
