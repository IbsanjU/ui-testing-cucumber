Feature: Google Search for revanab.com Articles
As a user
I want to search Google for articles on revanab.com
So that I can find specific content like the Cucumber BDD article

  Background:
    Given the user is on Google homepage

  @smoke @google
  Scenario: Search for revanab.com articles and find Cucumber BDD article
    When the user searches for "site:revanab.com cucumber"
    Then search results should be displayed
    When the user iterates through the search results
    Then the user should find "cucumber" related article
    And results should contain "revanab.com" in the URLs

  Scenario: Search specifically for UI testing article
    When the user searches for "site:revanab.com ui testing cucumber bdd"
    Then search results should be displayed
    When the user iterates through the search results
    Then the user should find "ui testing" related article

  @regression
  Scenario: Verify Google search functionality
    When the user searches for "site:revanab.com cucumber testing"
    Then search results should be displayed
    And results should contain "revanab.com" in the URLs
    And at least one result should mention "cucumber" or "testing"

  @regression @navigation
  Scenario: Navigate to any Cucumber related article
    When the user searches for "site:revanab.com cucumber"
    Then search results should be displayed
    When the user clicks on any "cucumber" related result
    Then the user should be on a revanab.com page