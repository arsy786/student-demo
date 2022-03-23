Feature: student feature


#  Describe an initial context (Given steps)
#  Describe an event (When steps)
#  Describe an expected outcome (Then steps)




  Scenario: A user gets all the students
    Given the following students
      | name            | email                   | dob         |
      | James May       | james.may@gmail.com     | 1999-01-01  |
      | Jeremy Clarkson | j.clarkson@hotmail.com  | 1995-12-25  |
    When the user submits a GET request to /api/v1/student/
    Then the api returns a list of all the students

  Scenario: A user gets a student
    Given the following student
      | name                | email             | dob         |
      | Cristiano Ronaldo   | cr7@outlook.com   | 1980-02-10  |
    When the user submits a GET request to /api/v1/student/16
    Then the api returns a list of the student

  Scenario: A user posts a new student
    When the user submits a POST request to /api/v1/student a new student with name Richard Hammond with email hammond@gmail.com with dob 1995-06-24
    Then it is in the database
    And it has an id of 17
    And it has an age

  Scenario: A user deletes a student
    Given the following student DELETE test
      | name            | email                   | dob         |
      | May James       | may.james@gmail.com     | 1980-11-01  |
    When the user deletes a student with id 18
    Then it is removed from the database
    And the database size is reduced by 1

  Scenario: A user updates a student
    Given the following student PUT test
      | name            | email                   | dob         |
      | Wayne Rooney    | wayne.rooney@gmail.com  | 1990-01-01  |
    When the user updates a student with id 19 to the following name Rayne Wooney and email wooney@gmail.com
    Then it is updated in the database
    And the student's name is now Rayne Wooney with email wooney@gmail.com but dob is still 1990-01-01
