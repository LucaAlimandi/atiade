package steps.playwright;

import com.microsoft.playwright.Page;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import net.jodah.failsafe.internal.util.Assert;

public class CAHome {
    private Page page;

    @Before
    public void beforeScenario() {
        page = PlaywrightManager.getPage();
    }

    @After
    public void afterScenario() {
        PlaywrightManager.close();
    }

    @Given("I am on the Cloud Academy home page")
    public void i_am_on_the_cloud_academy_home_page() {
        page.navigate("https://platform.qa.com/library/");
    }

    @When("I search for {string}")
    public void i_search_for(String query) {
        Allure.parameter("query", query);
        page.fill("//input[@class='react-autosuggest__input']", query);
    }

    @Then("I should see a list of courses related to {string}")
    public void i_should_see_a_list_of_courses_related_to(String query) {
        Allure.parameter("query", query);
        page.waitForSelector("h1[data-cy='search-result-title']");
        String resultText = page.textContent("h1[data-cy='search-result-title']");
        Assert.isTrue(resultText.contains(query), "Search result does not contain the query");
    }
}