Feature: Repairs Web Login

  Scenario: Valid login
    Given user is on the login page
    When user logs in with username "jmali" and password "Password@12345"
    And user selects profile and client
    Then user should be redirected to the dashboard