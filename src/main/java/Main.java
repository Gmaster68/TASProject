import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //pentru Java 11+
       // System.setProperty("webdriver.http.factory", "jdk-http-client");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");

        Thread.sleep(2000);

        submitButton.click();


        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        assertEquals("Received!", value);

        Thread.sleep(2000);

        driver.quit();
    }
}