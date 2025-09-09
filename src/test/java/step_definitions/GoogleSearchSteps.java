package step_definitions;

import io.cucumber.java.en.*;
import pages.GoogleSearchPage;
import pages.ArticlePage;
import utils.DriverManager;

import static org.junit.Assert.*;

public class GoogleSearchSteps {
    private GoogleSearchPage googleSearchPage;
    private ArticlePage articlePage;

    public GoogleSearchSteps() {
        googleSearchPage = new GoogleSearchPage(DriverManager.getDriver());
        articlePage = new ArticlePage(DriverManager.getDriver());
    }

    @Given("the user is on Google homepage")
    public void userIsOnGoogleHomepage() {
        googleSearchPage.navigateToGoogle();
        System.out.println("Navigated to Google homepage");
    }

    @When("the user searches for {string}")
    public void userSearchesFor(String searchTerm) {
        googleSearchPage.searchFor(searchTerm);
        System.out.println("Searched for: " + searchTerm);
    }

    @Then("search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        assertTrue("Search results should be displayed",
                googleSearchPage.areSearchResultsDisplayed());
        System.out.println("Search results are displayed");
    }

    @When("the user iterates through the search results")
    public void userIteratesThroughSearchResults() {
        googleSearchPage.printAllResults();
        System.out.println("Iterated through all search results");
    }

    @Then("the user should find {string} related article")
    public void userShouldFindArticle(String articleTitle) {
        assertTrue("Should find article: " + articleTitle,
                googleSearchPage.findArticleInResults(articleTitle));
        System.out.println("Found article: " + articleTitle);
    }

    @Then("the article link should contain {string}")
    public void articleLinkShouldContain(String urlPart) {
        assertTrue("Article link should contain: " + urlPart,
                googleSearchPage.verifyArticleLinkContains(urlPart));
        System.out.println("Article link contains: " + urlPart);
    }

    @Then("results should contain {string} in the URLs")
    public void resultsShouldContainInUrls(String domain) {
        assertTrue("Results should contain domain: " + domain,
                googleSearchPage.resultsContainDomain(domain));
        System.out.println("Results contain domain: " + domain);
    }

    @Then("at least one result should mention {string} or {string}")
    public void atLeastOneResultShouldMention(String keyword1, String keyword2) {
        assertTrue("Results should mention '" + keyword1 + "' or '" + keyword2 + "'",
                googleSearchPage.resultsContainKeywords(keyword1, keyword2));
        System.out.println("Results contain keywords: " + keyword1 + " or " + keyword2);
    }

    @When("the user clicks on the {string} result")
    public void userClicksOnResult(String articleTitle) {
        assertTrue("Should be able to click on article: " + articleTitle,
                googleSearchPage.clickOnArticle(articleTitle));
        System.out.println("Clicked on article: " + articleTitle);
    }

    @Then("the user should be on the article page")
    public void userShouldBeOnArticlePage() {
        // Wait a moment for page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue("Should be on the article page",
                articlePage.isOnArticlePage());
        System.out.println("User is on article page: " + articlePage.getCurrentUrl());
    }

    @Then("at least one result should be found")
    public void atLeastOneResultShouldBeFound() {
        assertTrue("At least one search result should be found",
                googleSearchPage.areSearchResultsDisplayed());
        System.out.println("At least one result was found");
    }
}