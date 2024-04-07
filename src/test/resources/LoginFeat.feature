Feature: Sauce Demo Login Functionality

  Background: User is on the Sauce Demo Login Page
    Given The user is on the Sauce Demo login page

  @happyPath
  Scenario: Standard user successful login
    When The user enters username "standard_user" and password "secret_sauce"
    And The user clicks on the login button
    Then The user should be redirected to the products page

  @happyPath
  Scenario: Adding an item to the cart after login
    Given The user has successfully logged in with "standard_user" and "secret_sauce"
    When The user navigates to the first product and clicks on the "Add to cart" button
    Then The product should be added to the cart
    And The cart should display "1" item

  @performance
  Scenario: Homepage load time should be acceptable
    When The user measures the load time for the Sauce Demo login page
    Then The load time should not exceed 2 seconds

  @security
  Scenario: Attempting to navigate to the products page without login
    Given The user is not logged in
    When The user attempts to navigate directly to the products page
    Then The user should be redirected to the login page
