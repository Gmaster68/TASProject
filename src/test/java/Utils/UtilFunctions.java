package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class UtilFunctions {


    public static void loginUser(WebDriver driver, String baseUrl) {
        driver.get(baseUrl);
        driver.findElement(By.id("user-name")).click();
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    public static void dropDownSelection(WebDriver driver, int selectedOption) {
        WebElement selectElement = driver.findElement(By.xpath("//div[@id='header_container']/div[2]/div/span/select"));
        Select dropdown = new Select(selectElement);
        dropdown.selectByIndex(selectedOption);
    }

    public static List<Float> getPricesFromInventory(WebDriver driver) {
        List<WebElement> priceElements = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
        return priceElements.stream().map(webElement -> Float.parseFloat(webElement.getText().replace("$", ""))).toList();
    }

    public static List<String> getItemNamesFromInventory(WebDriver driver) {
        //DO NOT GET THE ELEMENTS BY XPATH; for some reason it doesn't return the correct list
        List<WebElement> nameElements = driver.findElements(By.cssSelector("div.inventory_item_name"));
        return nameElements.stream().map(WebElement::getText).toList();
    }

    public static List<WebElement> getCartItems(WebDriver driver) {
        return driver.findElements(By.cssSelector("div.cart_item"));
    }

    public static boolean productTextIsCorrect(String text, String divContainer, WebDriver driver) {
        return getCartItems(driver).stream().map(webElement -> webElement.findElement(By.cssSelector(divContainer)).getText()).anyMatch(div -> div.equals(text));
    }

}
