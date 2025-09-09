package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"step_definitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/html-report",
                "json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                "timeline:target/cucumber-reports/timeline"
        },
        monochrome = true,
        tags = "not @ignore" // Skip scenarios tagged with @ignore
)
public class TestRunner {
    // This class will be empty - it's just used to run the Cucumber tests
}

// Additional test runners for different tag combinations

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"step_definitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke-test-report",
                "json:target/cucumber-reports/SmokeTest.json"
        },
        tags = "@smoke"
)
class SmokeTestRunner {
}

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"step_definitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/regression-test-report",
                "json:target/cucumber-reports/RegressionTest.json"
        },
        tags = "@regression"
)
class RegressionTestRunner {
}