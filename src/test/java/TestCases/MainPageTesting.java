package TestCases;

import com.google.common.collect.Comparators;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

import static Utils.UtilFunctions.*;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the Main Page: here we test that we have all the presented elements, that we can add and remove elements, all the functionalities
 */
public class MainPageTesting {
    private WebDriver driver;
    private String baseUrl;
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
    public void testMainPageTestItemsAddedToCart() throws Exception {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        assertEquals("Remove", driver.findElement(By.id("remove-sauce-labs-backpack")).getText());
        assertEquals("1", driver.findElement(By.linkText("1")).getText());
        sleep(1000);
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        assertEquals("Remove", driver.findElement(By.id("remove-sauce-labs-bike-light")).getText());
        assertEquals("2", driver.findElement(By.linkText("2")).getText());
        sleep(1000);
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        assertEquals("1", driver.findElement(By.linkText("1")).getText());
    }


    @Test
    public void testOrderItemsAToZ() throws Exception {
        dropDownSelection(driver, 0);
        sleep(1000);
        List<String> nameList = getItemNamesFromInventory(driver);
        boolean isNameListOrderedAscending = Comparators.isInOrder(nameList, Comparator.naturalOrder());
        assertTrue(isNameListOrderedAscending);
    }

    @Test
    public void testOrderItemsZToA() throws Exception {
        dropDownSelection(driver, 1);
        sleep(1000);
        List<String> nameList = getItemNamesFromInventory(driver);
        boolean isNameListOrderedAscending = Comparators.isInOrder(nameList, Comparator.reverseOrder());
        assertTrue(isNameListOrderedAscending);
    }

    @Test
    public void testMainPageOrderPriceLowToHighTestOne() throws Exception {
        dropDownSelection(driver, 2);
        sleep(1000);
        List<Float> pricesAfterSorting = getPricesFromInventory(driver);
        assertNotNull(pricesAfterSorting);
        boolean isSortedAscending = Comparators.isInOrder(pricesAfterSorting, Comparator.naturalOrder());
        assertTrue(isSortedAscending, "The prices should be sorted in ascending order.");
    }

    @Test
    public void testMainPageOrderPriceHighToLow() throws Exception {
        dropDownSelection(driver, 3);
        sleep(1000);
        List<Float> pricesAfterSorting = getPricesFromInventory(driver);
        boolean isSortedDescending = Comparators.isInOrder(pricesAfterSorting, Comparator.reverseOrder());
        assertTrue(isSortedDescending, "The prices should be sorted in ascending order.");
    }

    @Test
    public void testItemsShowAllDetails() {
        List<WebElement> webElements = driver.findElements(By.cssSelector("div.inventory_item"));
        webElements.forEach(
                webElement -> assertAll(
                        () -> assertFalse(webElement.findElements(By.cssSelector("div.inventory_item_name")).isEmpty(), "Missing item name"),
                        () -> assertFalse(webElement.findElements(By.cssSelector("div.inventory_item_price")).isEmpty(), "Missing item price"),
                        () -> assertFalse(webElement.findElements(By.cssSelector("div.inventory_item_desc")).isEmpty(), "Missing item description"),
                        () -> assertFalse(webElement.findElements(By.cssSelector(".inventory_item_img img")).isEmpty(), "Missing item picture"))
        );
    }


    @Test
    public void sideBarElements() throws InterruptedException {
        driver.get("https://www.saucedemo.com/inventory.html");
        driver.findElement(By.id("react-burger-menu-btn")).click();
        sleep(100);
        assertEquals("All Items", driver.findElement(By.id("inventory_sidebar_link")).getText());
        assertEquals("About", driver.findElement(By.id("about_sidebar_link")).getText());
        assertEquals("Logout", driver.findElement(By.id("logout_sidebar_link")).getText());
        assertEquals("Reset App State", driver.findElement(By.id("reset_sidebar_link")).getText());
    }

    @Test
    public void sideBarLogOut() throws InterruptedException {
        driver.get("https://www.saucedemo.com/inventory.html");
        driver.findElement(By.id("react-burger-menu-btn")).click();
        sleep(100);
        driver.findElement(By.id("logout_sidebar_link")).click();
        assertFalse(driver.getCurrentUrl().contains("/inventory"));
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
