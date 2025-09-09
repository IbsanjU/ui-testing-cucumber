package step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverManager;
import utils.ConfigManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.Duration;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());

        // Set implicit wait
        DriverManager.getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigManager.getImplicitWait()));
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Scenario status: " + scenario.getStatus());

        // Take screenshot if scenario fails and configuration allows it
        if (scenario.isFailed() &&
                Boolean.parseBoolean(ConfigManager.getProperty("take.screenshot.on.failure", "true"))) {

            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
                System.out.println("Screenshot taken for failed scenario: " + scenario.getName());
            } catch (Exception e) {
                System.err.println("Failed to take screenshot: " + e.getMessage());
            }
        }

        System.out.println("Finished scenario: " + scenario.getName());
        System.out.println("----------------------------------------");

        // Clean up driver
        DriverManager.quitDriver();
    }
}