Feature: User Login Functionality
  As a registered user
  I want to log into the application
  So that I can access my dashboard

  Background:
    Given the user is on the login page

  @smoke
  Scenario: Successful login with valid credentials
    When the user enters username "john.doe@example.com"
    And the user enters password "SecurePass123!"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
    And the welcome message should display "Welcome back, John!"

  @regression @negative
  Scenario: Login fails with invalid password
    When the user enters username "john.doe@example.com"
    And the user enters password "WrongPassword"
    And the user clicks the login button
    Then an error message "Invalid credentials" should be displayed
    And the user should remain on the login page

  @regression
  Scenario Outline: Login validation with different invalid inputs
    When the user enters username "<username>"
    And the user enters password "<password>"
    And the user clicks the login button
    Then an error message "<error_message>" should be displayed

    Examples:
      | username              | password     | error_message                |
      |                       | ValidPass123 | Username is required         |
      | john.doe@example.com  |              | Password is required         |
      | invalid-email         | ValidPass123 | Please enter a valid email   |