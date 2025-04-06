package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Calculator;

@DisplayName("Calculator Tests")
public class CalculatorTest {
	
    Calculator calc;
    
	@BeforeEach
	void setUp() throws Exception {
		
		 calc = new Calculator();
		 System.out.println("Before Each Test");
	}
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Before All Test: setup complete");
	}

	@AfterEach
	void afterEach() {
		System.out.println("After Each Test");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("After All Test: cleanup complete");
	}

	
	@Test
    @Order(1)
    @DisplayName("Test Add")
    @Timeout(value = 100, unit=TimeUnit.MILLISECONDS)
    void testAdd() {
        assertEquals(7, calc.add(1, 0, 5, 1));
        assertNotEquals(9, calc.add(7, 1), "Adding 7 and 1 shouldn't result 9");
        //assertEquals(1, calc.add(5), "Addition failed");
    }
	
   @ParameterizedTest
   @Order(2)
   @CsvSource({
       "1, 2, 3, 6",
       "5, 0, 4, 9",
       "6, 1, 0, 7",
   })
   
   @DisplayName("Test Add with Parameterized Values")
   void testAddParameterized(int n1, int n2, int n3, int expectedSum) {
       assertEquals(expectedSum, calc.add(n1, n2, n3));
   }

	@Test
    @Order(3)
    @DisplayName("Test divide")
    void testDivide() {
        assertEquals(5, calc.divide(25, 5));
        assertEquals(-1, calc.divide(3, -3), "3 divided -3 should be -1");
        //assertEquals(8, calc.divide(30, 5), "Divide failed");

    }
	
   @Test
   @Order(4)
   @DisplayName("Test divide by zero")
    void testDivideByZero() { 
	   Exception exception = assertThrows(ArithmeticException.class, () -> calc.divide(7, 0));
	   assertEquals("Cannot divide by zero", exception.getMessage());
    }
   
   @Test
   @Order(5)
   @DisplayName("Test factorial of positive numbers")
   void testFactorial() {
       assertEquals(1, calc.factorial(1));
       assertEquals(2, calc.factorial(2), "Factorial of 2 should be 2");
       assertEquals(6, calc.factorial(3));
       //assertEquals(2, calc.factorial(0), "Factorial failed");
   }
   
   @Test
   @Order(6)
   @DisplayName("Test factorial of negative numbers")
   void testFactorialNegative() {
       Exception exception = assertThrows(IllegalArgumentException.class, () -> calc.factorial(-7));
       assertEquals("Negative input", exception.getMessage());
   }
   
   @Test
   @Order(7)
   @DisplayName("Test Multiple Assertions")
   void testMultipleAssertions() {
       int addResult = calc.add(1, 2);
       int divideResult = calc.divide(9, 3);

       assertAll("Multiple assertions",
           () -> assertEquals(3, addResult),
           () -> assertEquals(3, divideResult)
       );
   }

}
