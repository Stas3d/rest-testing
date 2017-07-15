package com.epam.company;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber/report.json"},
        tags = {"~@Wip"},
        features = "src/test/resources/features"
)

public class RunnerTest {
}
