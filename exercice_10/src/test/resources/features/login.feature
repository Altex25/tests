Feature: User login
  As a user
  I want to log in to my account
  So that I can access the application and place orders

  Scenario: Login succeeds and redirects to the home page
    Given a registered user "carol" with password "pwd"
    When "carol" logs in with password "pwd"
    Then the login succeeds and redirects to the "home" page

  Scenario: Login fails with wrong credentials
    Given a registered user "carol" with password "pwd"
    When "carol" logs in with password "wrong"
    Then the login fails with an error message

  Scenario: Login fails for an unknown user
    Given no registered user with username "ghost"
    When "ghost" logs in with password "whatever"
    Then the login fails with an error message
