package main.najah.test;


//import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


@Suite
//@SelectClasses({CalculatorTest.class,ProductTest.class,RecipeBookTest.class,UserServiceSimpleTest.class,RecipeTest.class})
@SelectPackages(value = { "main.najah.test" })

class TestSuite {

}
