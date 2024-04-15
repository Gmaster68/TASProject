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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GamingSubSectionTest {
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
    public void gamingSubSectionTest() throws InterruptedException {
        driver.manage().window().maximize();
        driver.findElements(By.xpath("//*[@href='/gaming']")).get(0).click();
        String actualText = driver.findElement(By.xpath("//span[@class='yt-core-attributed-string yt-core-attributed-string--white-space-pre-wrap']")).getText();
        String expectedTextRO = "Jocuri";
        String expectedTextEN = "Gaming";
        assertTrue(expectedTextRO.equals(actualText) || expectedTextEN.equals(actualText));
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
