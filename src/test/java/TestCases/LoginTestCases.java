package TestCases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.Assert.*;


/**
 * Login test cases: Here we test our selected site to see the possible outcomes of logging in: either success, failure due to nonexistent user, or locked out user.
 * there are other users created to test other visual or functional issues, but we will leave them aside, as this page is merely for logging in.
 */
public class LoginTestCases {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;


    @Before
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        baseUrl = "https://www.saucedemo.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void loginSuccessful() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.id("user-name")).click();
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        assertTrue(driver.getCurrentUrl().contains("/inventory"));
    }

    @Test
    public void testLoginFailed_lockedUser() {
        driver.get(baseUrl);
        driver.findElement(By.id("user-name")).click();
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        try {
            assertEquals("Epic sadface: Sorry, this user has been locked out.", driver.findElement(By.xpath("//div[@id='login_button_container']/div/form/div[3]/h3")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @Test
    public void testLoginFailed_UserNotExistent() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.id("user-name")).click();
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("NoUser");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test123");
        driver.findElement(By.id("login-button")).click();
        try {
            assertEquals("Epic sadface: Username and password do not match any user in this service", driver.findElement(By.xpath("//div[@id='login_button_container']/div/form/div[3]/h3")).getText());
        } catch (Error e) {
            verificationErrors.append(e);
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!verificationErrorString.isEmpty()) {
            fail(verificationErrorString);
        }
    }
}
