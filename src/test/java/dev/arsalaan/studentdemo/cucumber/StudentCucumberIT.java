package dev.arsalaan.studentdemo.cucumber;

import dev.arsalaan.studentdemo.StudentDemoApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = {StudentDemoApplication.class,
        StudentCucumberIT.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/java/resources/features")
public class StudentCucumberIT {


}
