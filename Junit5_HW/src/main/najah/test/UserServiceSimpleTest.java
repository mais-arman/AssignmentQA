package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.UserService;

@Execution(value = ExecutionMode.CONCURRENT)
public class UserServiceSimpleTest {
	UserService userService ;

	@BeforeEach
	void setUp() throws Exception {
		
        userService = new UserService();

	}


	@Test
	@DisplayName("Test Valid Email")
	@Timeout(1)
	void testValidEmail() {
	    assertTrue(userService.isValidEmail("user@gmail.com"), "The email valid");
	    assertFalse(userService.isValidEmail("invalid-email"), "The email invalid because it lacks '@' or '.'");
	    assertFalse(userService.isValidEmail("user@gmail"), "The email invalid because it lacks a proper domain extension");
	    assertFalse(userService.isValidEmail(null), "The email invalid because it is null");
	}

	@Test
	@DisplayName("Test Authenticate")
	void testAuthenticate() {
	    assertTrue(userService.authenticate("admin", "1234"), "Authentication succeed");
	    assertFalse(userService.authenticate("user", "1234"), "Authentication fail with an incorrect username");
	    assertFalse(userService.authenticate("admin", "0000"), "Authentication fail with an incorrect password");
	    assertFalse(userService.authenticate("", ""), "Authentication fail with empty username and password");
	    assertFalse(userService.authenticate(null, null), "Authentication fail with null username and password");
	}
	
	@Test
    @DisplayName("Test authentication with multiple assertions")
    void testAuthenticateWithMultipleAssertions() {
        assertAll("Authentication Cases",
            () -> assertTrue(userService.authenticate("admin", "1234"), "Authentication succeed"),
            () -> assertFalse(userService.authenticate("user", "1234"), "Authentication fail with an incorrect username"),
            () -> assertFalse(userService.authenticate("admin", "0000"), "Authentication fail with an incorrect password"),
            () -> assertFalse(userService.authenticate("", ""), "Authentication fail with empty username and password"),
            () -> assertFalse(userService.authenticate(null, null), "Authentication fail with null username and password")
        );
    }
	
	@ParameterizedTest
    @DisplayName("Parameterized Test for Email Validation")
    @CsvSource({
        "user@gmail.com, true",
        "invalid-email, false",
        "user@gmail, false",
        "user@domain, false",
        "null, false"
    })
    void testValidEmailParameterized(String email, boolean expected) {
        assertEquals(expected, userService.isValidEmail(email));
    }

	
	@Test
    @Disabled
    @DisplayName("Test Authenticate with wrong user name")
	void testAuthenticateWithWrongUserName() {
	    assertTrue(userService.authenticate("mais", "1234"), "Authentication failed");
	    
	}
	
	@Test
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	@DisplayName("Test Timeout")
	void testTimeout() throws InterruptedException {
		Thread.sleep(50);
		//Thread.sleep(200);
		assertTrue(true);
	}
}
