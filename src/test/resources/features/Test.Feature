Feature: Payment Card API Testing
  As a User
  I want to perform different actions with the card
  So that I can achieve the successful results

  Scenario: AC-01 User receives 'unauthorized' message when request without authentication
    Given User send request to get card with name 67c1e085-ff59-492a-9613-84c9d4f6411e
    Then The status code is 401

  Scenario: AC-02 Authenticated User can receive the card info
    Given User provides authentication
    When User send request to get card with name 67c1e085-ff59-492a-9613-84c9d4f6411e
    And The status code is 200
    Then response includes the following
      |id        |67c1e085-ff59-492a-9613-84c9d4f6411e |
      |expMonth  |1                                    |
      |expYear   |2021                                 |
      |status    |ACTIVE                               |
      |currency  |USD                                  |
      |balance   |10000                                |

  Scenario: AC-03 User tries to receive a non-existing card
    Given User provides authentication
    When User send request to get card with name 0000
    Then The status code is 400

  Scenario: AC-04 User can receive the list of all existing cards
    Given User provides authentication
    When User send request to receive all cards in system
    Then The status code is 200
    And User verify that response has 1 cards items inside