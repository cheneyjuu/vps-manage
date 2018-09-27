package com.swiftcode.cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    monochrome = true,
    plugin = {"pretty", "html:target/cucumber"},
    features = "src/test/features")
public class CucumberTest {
}
