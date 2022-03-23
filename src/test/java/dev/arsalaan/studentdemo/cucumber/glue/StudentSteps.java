package dev.arsalaan.studentdemo.cucumber.glue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.arsalaan.studentdemo.model.Student;
import dev.arsalaan.studentdemo.repository.StudentRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;


public class StudentSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Student> expectedStudents;
    private List<Student> actualStudents;
    private HttpHeaders headers;
    private ResponseEntity responseEntity;
    private String responseBodyJSON;

    //Methods annotated with the @Before annotation are run before each test.
    //Here it is used to initialise empty student lists and to delete any entries in studentRepository (DB)
    @Before
    public void setup() {
        expectedStudents = new ArrayList<>();
        actualStudents = new ArrayList<>();
        headers = new HttpHeaders();
        studentRepository.deleteAll();
    }

    //Scenario 1: A user gets all the students
    @Given("^the following students$")
    public void givenTheFollowingStudents(final List<Student> students) {

        expectedStudents.addAll(students);      //Saves gherkin test students to expectedStudents List
        studentRepository.saveAll(students);    //Saves gherkin test students to studentRepository (DB)

    }

    @When("^the user submits a GET request to /api/v1/student/$")
    public void whenTheUserRequestsAllTheStudents() throws JsonProcessingException {

        //.getForEntity retrieves all students in the DB by doing a GET on the URL.
        //The response is converted and stored in a ResponseEntity.
        //ResponseEntity represents the whole HTTP response: status code, headers, and body.
        responseEntity = testRestTemplate.getForEntity("/api/v1/student/", String.class);

        //Retrieves the body of the ResponseEntity and stores as a String.
        responseBodyJSON = responseEntity.getBody().toString();

        //Deserialize JSON (String) content into a Java object (Java objects of type list in this case).
        Student[] responseBodyJava = objectMapper.readValue(responseBodyJSON, Student[].class);
        
        //Converts the Student array to a fixed-size List object and adds the students from the GET request (DB)
        // to the actualStudents List. (Does not bypass the DB as the GET response body is a result of API Endpoint call)
        actualStudents.addAll(Arrays.asList(responseBodyJava));

    }

    @Then("^the api returns a list of all the students$")
    public void thenAllTheStudentsAreReturned() {

        //Testing status code is: 200 Success
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //Testing size of gherkin table to DB table
        Assertions.assertEquals(expectedStudents.size(), actualStudents.size());

        //For each actual and expected students list:
        //Testing that the actualStudent and corresponding expectedStudent objects have exactly the same data
        //This is equivalent to getting the index of every student from each list and checking their attributes match
        IntStream.range(0, actualStudents.size())
                .forEach(index -> org.assertj.core.api.Assertions
                        .assertThat(actualStudents.get(index))
                        .usingRecursiveComparison()
                        .isEqualTo(expectedStudents.get(index)));

        //Age is a dynamic variable calculated in the getter using the LocalDate dob variable, thus specific testing is needed for age
        //NOTE: There is no need to test if the expectedStudents and actualStudents ages match as that is covered in the above test
        //Testing that the original "null" value for each expectedStudents age is updated and no longer null
        IntStream.range(0, expectedStudents.size())
                .forEach(index -> Assertions
                        .assertNotNull(expectedStudents.get(index).getAge()));

    }

    //Scenario 2: A user gets a student
    @Given("^the following student$")
    public void givenTheFollowingStudent(final Student student) {

        expectedStudents.add(student);      //Saves gherkin test student to expectedStudents List
        studentRepository.save(student);    //Saves gherkin test student to studentRepository (DB)

    }

    @When("^the user submits a GET request to /api/v1/student/(\\d+)$")
    public void whenTheUserRequestTheStudent(final Long studentId) throws JsonProcessingException {

        //.getForEntity retrieves all students in the DB by doing a GET on the URL.
        //The response is converted and stored in a ResponseEntity.
        //ResponseEntity represents the whole HTTP response: status code, headers, and body.
        responseEntity = testRestTemplate.getForEntity("/api/v1/student/{studentId}", String.class, studentId);

        //Retrieves the body of the ResponseEntity and stores as a String.
        responseBodyJSON = responseEntity.getBody().toString();

        //Deserialize JSON (String) content into a Java object (Java objects of type list in this case).
        Student responseBodyJava = objectMapper.readValue(responseBodyJSON, Student.class);

        //Converts the Student array to a fixed-size List object and adds the students from the GET request (DB)
        // to the actualStudents List. (Does not bypass the DB as the GET response body is a result of API Endpoint call)
        actualStudents.addAll(Arrays.asList(responseBodyJava));

    }

    @Then("^the api returns a list of the student$")
    public void thenTheStudentIsReturned() {

        //Testing status code is: 200 Success
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //Testing size of gherkin table to DB table
        Assertions.assertEquals(expectedStudents.size(), actualStudents.size());

        //For each actual and expected students list:
        //Testing that the actualStudent and corresponding expectedStudent objects have exactly the same data
        //This is equivalent to getting the index of every student from each list and checking their attributes match
        IntStream.range(0, actualStudents.size())
                .forEach(index -> org.assertj.core.api.Assertions
                        .assertThat(actualStudents.get(index))
                        .usingRecursiveComparison()
                        .isEqualTo(expectedStudents.get(index)));

        //Age is a dynamic variable calculated in the getter using the LocalDate dob variable, thus specific testing is needed for age
        //NOTE: There is no need to test if the expectedStudents and actualStudents ages match as that is covered in the above test
        //Testing that the original "null" value for each expectedStudents age is updated and no longer null
        IntStream.range(0, expectedStudents.size())
                .forEach(index -> Assertions
                        .assertNotNull(expectedStudents.get(index).getAge()));

    }


    //Scenario 3: A user posts a new student
    @When("^the user submits a POST request to /api/v1/student a new student with name (.*) with email (.*) with dob (.*)$")
    public void whenTheUserPostsANewStudent(final String name, final String email, final LocalDate dob) throws JsonProcessingException {

        //Creating new student using the POST request details provided
        final Student student = new Student(name, email, dob);

        //Saving gherkin test student to expectedStudents List
        //(*Student ID remains null as gherkin data table not used in this Scenario*)
        expectedStudents.add(student);

        //.postForEntity creates a new resource by POSTing the given object to the URL, and returns the response as ResponseEntity.
        //The request parameter can be a HttpEntity in order to add additional HTTP headers to the request.
        responseEntity = testRestTemplate.postForEntity("/api/v1/student/", student, String.class);

        //Cannot add the student via responseEntity.getBody() as it is empty for a POST function (does not return a body).
        //Adding all students (only 1) from the Repository (DB) to the actualStudents List.
        actualStudents.addAll(studentRepository.findAll());

        //(*Updating ID in expectedStudent List manually*)
        expectedStudents.get(0).setId(actualStudents.get(0).getId());

    }

    @Then("^it is in the database$")
    public void thenItIsInTheDatabase() {

        //Testing status code is: 201 Created
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        //Testing that the POST request worked by checking the actualStudents (DB) size is equal to 1
        Assertions.assertEquals(actualStudents.size(), 1);

        //Testing that the Database Student (comes from POST) and Java Student (comes from Gherkin) objects have exactly the same data
        IntStream.range(0, actualStudents.size())
                .forEach(index -> org.assertj.core.api.Assertions
                        .assertThat(actualStudents.get(index)).usingRecursiveComparison()
                        .isEqualTo(expectedStudents.get(index)));

    }

    @And("^it has an id of (\\d+)$")
    public void andItHasId17(Long studentId) {

        //Only 1 student present in DB (as @Before clears the repository) hence using .get(0) instead of using Java 8 Streams
        //Testing that the Student in the DB has an ID and that it is equal to 17
        Assertions.assertNotNull(actualStudents.get(0).getId());
        Assertions.assertEquals(actualStudents.get(0).getId(), studentId);

    }

    @And("^it has an age$")
    public void andItHasAnAge() {

        //Testing that the original "null" value for expectedStudent age is updated and no longer null
        Assertions.assertNotNull(actualStudents.get(0).getAge());

    }

    //Scenario 4: A user deletes a student
    @Given("^the following student DELETE test$")
    public void givenTheFollowingStudentDelete(final Student student) {

        expectedStudents.add(student);      //Saves gherkin test student to expectedStudents List
        studentRepository.save(student);    //Saves gherkin test student to studentRepository (DB)

    }

    @When("^the user deletes a student with id (\\d+)$")
    public void whenTheUserDeletesAStudent(final Long studentId) {

        //HttpHeaders setContentType(...): Indicates that the request body format is JSON
        //HttpEntity: Represents an HTTP request or response entity, consisting of headers and body.
        //url: REST API Endpoint path being used for response/request
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        String url = "/api/v1/student/{studentId}";

        //.exchange executes the HTTP method to the given URI template,
        // writing the given request entity to the request, and returns the response as ResponseEntity.
        responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.DELETE,
                httpEntity,
                String.class,
                studentId);

        //testRestTemplate.delete("/api/v1/student/{id}", studentId, Student.class); ALTERNATIVE (but no Response stored!)

        //Cannot add the student via responseEntity.getBody() as it is empty for a DELETE function (does not return a body).
        //Adding all students (technically 0) from the Repository (DB) to the actualStudents List.
        actualStudents.addAll(studentRepository.findAll());

    }

    @Then("^it is removed from the database$")
    public void thenItIsRemovedFromTheDatabase() throws JsonProcessingException {

        //Testing status code is: 204 No Content
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        //Confirming that the actualStudents (DB) List is indeed empty
        Assertions.assertTrue(actualStudents.isEmpty());

    }

    @And("^the database size is reduced by 1$")
    public void theDatabaseSizeIsReducedBy1() {

        //Testing that the gherkin expectedStudents size (1) minus 1 is equal to the DB actualStudents size (0)
        //Before DELETE vs. After DELETE
        Assertions.assertEquals(expectedStudents.size()-1, actualStudents.size());

    }


    //Scenario 5: A user updates a student
    @Given("^the following student PUT test$")
    public void givenTheFollowingStudentPut(final Student student) {

        expectedStudents.add(student);      //Saves gherkin test student to expectedStudents List
        studentRepository.save(student);    //Saves gherkin test student to studentRepository (DB)

    }

    @When("^the user updates a student with id (\\d+) to the following name (.*) and email (.*)$")
    public void whenTheUserUpdatesAStudent(final Long id, final String name, final String email) {

        //Creating new studentUpdate using the PUT request details provided
        //Age (dob) is unchanged therefore can simply get the original dob
        Student studentUpdate = new Student(name, email, expectedStudents.get(0).getDob());

        //HttpHeaders setContentType(...): Indicates that the request body format is JSON
        //HttpEntity: Represents an HTTP request or response entity, consisting of headers and body.
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(studentUpdate, headers);

        //.exchange executes the HTTP method to the given URI template,
        // writing the given request entity to the request, and returns the response as ResponseEntity.
        responseEntity = testRestTemplate.exchange(
                "/api/v1/student/{id}",
                HttpMethod.PUT,
                httpEntity,
                String.class,
                id);

        //Cannot add the student via responseEntity.getBody() as it is empty for a PUT function (does not return a body).
        //Adding all students (only the updated 1) from the Repository (DB) to the actualStudents List.
        actualStudents.addAll(studentRepository.findAll());

    }

    @Then("^it is updated in the database$")
    public void thenItIsUpdatedInTheDatabase() throws JsonProcessingException {

        //Testing status code is: 200 Success
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //Testing that the DB (actualStudents) list still only contains 1 Student
        Assertions.assertEquals(actualStudents.size(), 1);

    }

    @And("^the student's name is now Rayne Wooney with email wooney@gmail.com but dob is still 1990-01-01$")
    public void andTheStudentsNameIsNowRayneWooneyWithEmailWooneyAtGmailDotCom() {

        //Testing that the actualStudent (comes from PUT) and Java Student (Gherkin) objects do NOT have exactly the same data
        IntStream.range(0, actualStudents.size())
                .forEach(index -> org.assertj.core.api.Assertions
                        .assertThat(actualStudents.get(index)).usingRecursiveComparison()
                        .isNotEqualTo(expectedStudents.get(index)));

        //Testing the expectedStudent and actualStudent have different Names
        Assertions.assertNotEquals(expectedStudents.get(0).getName(), actualStudents.get(0).getName());

        //Testing the expectedStudent and actualStudent have different Emails
        Assertions.assertNotEquals(expectedStudents.get(0).getEmail(), actualStudents.get(0).getEmail());

        //Testing the expectedStudent and actualStudent still have the same dob (age)
        Assertions.assertEquals(expectedStudents.get(0).getDob(), actualStudents.get(0).getDob());

        //Testing the actualStudent in DB attributes to the gherkin stated attributes.
        Assertions.assertEquals(actualStudents.get(0).getName(), "Rayne Wooney");
        Assertions.assertEquals(actualStudents.get(0).getEmail(), "wooney@gmail.com");
        Assertions.assertEquals(actualStudents.get(0).getDob().toString(), "1990-01-01");

    }

}
