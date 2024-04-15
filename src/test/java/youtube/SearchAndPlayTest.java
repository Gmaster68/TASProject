package youtube;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static Utils.UtilFunctions.loginUser;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SearchAndPlayTest {

    private static WebDriver driver;
    private static String baseUrl;
    private static final String VIDEO_TITLE = "¥$, Ye, Ty Dolla $ign - VULTURES (Juice Version) feat. Bump J & Lil Durk";

    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @Before
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        baseUrl = Strings.YOUTUBE_BASE_URL;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
        driver.get(baseUrl);
        driver.findElements(By.xpath("//button[@class='yt-spec-button-shape-next yt-spec-button-shape-next--filled yt-spec-button-shape-next--mono yt-spec-button-shape-next--size-m']"))
                .get(0).click();
    }

    @Test
    public void testSearchForAVideo() throws InterruptedException {
        js.executeScript("arguments[0].value='"+ VIDEO_TITLE +"';", driver.findElement(By.xpath("//input[@id='search']")));
        Thread.sleep(2000);
        driver.findElement(By.id("search-form")).submit();
        Thread.sleep(2000);
        driver.findElements(By.xpath("//*[@id='video-title']")).get(0).click();
        Thread.sleep(2000);
        String actualTitle = driver.findElements(By.xpath("//*[@class='style-scope ytd-watch-metadata']")).get(0).getText();
        assertTrue(actualTitle.contains(VIDEO_TITLE));
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
