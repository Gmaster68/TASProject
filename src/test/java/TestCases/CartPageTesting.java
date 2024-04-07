package TestCases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static Utils.UtilFunctions.*;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class CartPageTesting {

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
        loginUser(driver, baseUrl);
    }

    @Test
    public void givenCartEmpty_thenPageShouldHaveNoItems() throws InterruptedException {
        driver.findElement(By.xpath("//div[@id='shopping_cart_container']/a")).click();
        sleep(100);
        assertTrue(driver.getCurrentUrl().contains("/cart"));
        assertTrue(getCartItems(driver).isEmpty());
    }


    @Test
    public void testCartHasProductWithCorrectDetails() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        assertEquals("2", driver.findElement(By.className("shopping_cart_badge")).getText());
        driver.findElement(By.className("shopping_cart_badge")).click();
        assertTrue(productTextIsCorrect("Sauce Labs Backpack", "div.inventory_item_name", driver));
        assertTrue(productTextIsCorrect("Sauce Labs Bike Light", "div.inventory_item_name", driver));
        assertTrue(productTextIsCorrect("$29.99", "div.inventory_item_price", driver));
        assertTrue(productTextIsCorrect("$9.99", "div.inventory_item_price", driver));
    }


    @Test
    public void testRemovalOfOneProductFromCart() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.className("shopping_cart_badge")).click();
        assertEquals("2", driver.findElement(By.className("shopping_cart_badge")).getText());
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
    }


    @After
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!verificationErrorString.isEmpty()) {
            fail(verificationErrorString);
        }
    }


}
