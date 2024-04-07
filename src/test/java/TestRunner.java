import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources", // or path to your specific feature file
        glue = "test.java", // ensure this points correctly to your step definitions' package
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class TestRunner {
}
