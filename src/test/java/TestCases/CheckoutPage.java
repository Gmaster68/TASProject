package TestCases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static Utils.UtilFunctions.getPricesFromInventory;
import static Utils.UtilFunctions.loginUser;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutPage {
    private static WebDriver driver;
    private static String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;


    @Before
    public void setUp() {
        driver = new ChromeDriver();
        baseUrl = "https://www.saucedemo.com";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
        goToCheckoutFirstStep();
    }

    //FIRST STEP

    @Test
    public void testCheckoutGoesToCorrectFirstPage() {
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-one"));
    }


    @Test
    public void testErrorValidationFirstStep() {
        driver.findElement(By.id("continue")).click();
        assertEquals("Error: First Name is required", driver.findElement(By.className("error-message-container")).getText());
        driver.findElement(By.id("first-name")).sendKeys("randomName");
        driver.findElement(By.id("continue")).click();
        assertEquals("Error: Last Name is required", driver.findElement(By.className("error-message-container")).getText());
        driver.findElement(By.id("last-name")).sendKeys("randomName");
        driver.findElement(By.id("continue")).click();
        assertEquals("Error: Postal Code is required", driver.findElement(By.className("error-message-container")).getText());
        driver.findElement(By.id("postal-code")).sendKeys("randomCode");
        driver.findElement(By.id("continue")).click();
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-two"));
    }

    @Test
    public void testCancellation() {
        driver.findElement(By.id("cancel")).click();
        assertFalse(driver.getCurrentUrl().contains("/checkout-step-one"));
    }

    //SECOND STEP


    @Test
    public void testCheckoutGoesToCorrectSecondPage() {
        goToCheckoutSecondStep();
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-two"));
    }

    @Test
    public void testPricesInSecondStepAreCorrect() {
        goToCheckoutSecondStep();
        double subtotalPrice = getPricesFromInventory(driver).stream().mapToDouble(Float::doubleValue).sum();
        subtotalPrice = Math.round(subtotalPrice * 100.0) / 100.0; // clean the value to that it will not return 39.97999954223633, gotta love the floating-point number representation! -_-
        assertTrue(driver.findElement(By.className("summary_subtotal_label")).getText().contains(Double.toString(subtotalPrice)));
        double tax = Double.parseDouble(driver.findElement(By.className("summary_tax_label")).getText().replace("Tax: $", ""));
        assertTrue(driver.findElement(By.className("summary_total_label")).getText().contains(Double.toString(subtotalPrice + tax)));
    }

    @Test
    public void testFinishOrder() {
        goToCheckoutSecondStep();
        driver.findElement(By.id("finish")).click();
        assertTrue(driver.getCurrentUrl().contains("checkout-complete"));
        assertTrue(driver.findElement(By.className("complete-header")).getText().contains("Thank you for your order!"));
        driver.findElement(By.id("back-to-products")).click();
        assertTrue(driver.getCurrentUrl().contains("/inventory"));
    }

    @After
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!verificationErrorString.isEmpty()) {
            fail(verificationErrorString);
        }
    }

    private static void goToCheckoutFirstStep() {
        loginUser(driver, baseUrl);
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.className("shopping_cart_badge")).click();
        driver.findElement(By.id("checkout")).click();
    }

    private static void goToCheckoutSecondStep() {
        driver.findElement(By.id("last-name")).sendKeys("randomName");
        driver.findElement(By.id("first-name")).sendKeys("randomName");
        driver.findElement(By.id("postal-code")).sendKeys("randomCode");
        driver.findElement(By.id("continue")).click();
    }
}
