import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class StepDefinitions {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Setup your WebDriver here. You might want to make this configurable based on the browser.
        driver = new ChromeDriver();
    }

    @Given("The user is on the Sauce Demo login page")
    public void user_on_login_page() {
        driver.get("https://www.saucedemo.com/v1/");
    }

    @When("The user enters username {string} and password {string}")
    public void user_enters_credentials(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @And("The user clicks on the login button")
    public void user_clicks_login() {
        driver.findElement(By.id("login-button")).click();
    }

    @Then("The user should be redirected to the products page")
    public void user_redirected_to_products() {
        assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @After
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
