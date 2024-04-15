package youtube;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.fail;

public class AddToQueueTest {

    private static WebDriver driver;
    private static String baseUrl;
    private static final String VIDEO_TITLE = "¥$, Ye, Ty Dolla $ign - VULTURES (Juice Version) feat. Bump J & Lil Durk";

    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @Before
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        baseUrl = Strings.YOUTUBE_TRENDING_URL;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
        driver.get(baseUrl);
        driver.findElements(By.xpath("//*[@class='VfPpkd-LgbsSe VfPpkd-LgbsSe-OWXEXe-k8QpJ VfPpkd-LgbsSe-OWXEXe-dgl2Hf nCP5yc AjY5Oe DuMIQc Gu558e']"))
                .get(0).click();
    }

    @Test
    public void addToQueueTest() throws InterruptedException {
        Thread.sleep(6000);
        String expectedText = driver.findElements(By.xpath("//*[@class='style-scope ytd-video-renderer']")).get(0).getText();
        driver.findElements(By.xpath("//button[@id='button' and @class='style-scope yt-icon-button' and @aria-label='Action menu']")).get(0).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@class='style-scope ytd-menu-service-item-renderer']")).click();
        Thread.sleep(2000);
        String actualText = driver.findElement(By.xpath("//*[@class='miniplayer-title style-scope ytd-miniplayer']"))
                .getText();
        Assertions.assertTrue(expectedText.contains(actualText));
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
