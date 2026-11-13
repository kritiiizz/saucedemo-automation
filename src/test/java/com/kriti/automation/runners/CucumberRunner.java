package com.kriti.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runs the Gherkin feature files through TestNG, scenarios in parallel.
 * Thread count comes from data-provider-thread-count in testng.xml.
 * Filter by tag from the command line: mvn test -Dcucumber.filter.tags="@smoke"
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.kriti.automation.bdd",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class CucumberRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
