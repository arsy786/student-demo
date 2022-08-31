package dev.arsalaan.studentdemo.controller;

import dev.arsalaan.studentdemo.model.Course;
import dev.arsalaan.studentdemo.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();

        if (courses == null || courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long courseId) {
        Course course = courseService.getCourseById(courseId);

        if (course == null) {
            return new ResponseEntity("Course with id " + courseId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Course>(course, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity createCourse(@Valid @RequestBody Course course) {
        courseService.addCourse(course);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity updateCourse(@PathVariable("courseId") Long courseId, @RequestBody Course course) {
        int val = courseService.updateCourse(courseId, course.getName(), course.getDuration());

        if (val == 1) {
            return new ResponseEntity("Course with id " + courseId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity deleteCourseById(@PathVariable("courseId") Long courseId) {

        boolean exists = courseService.deleteCourseById(courseId);

        if (exists) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity("Course with id " + courseId + " does not exist", HttpStatus.NOT_FOUND);
    }


}
