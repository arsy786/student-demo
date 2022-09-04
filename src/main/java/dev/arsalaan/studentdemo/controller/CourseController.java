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

    // Dependency Injection
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // [GET] View All Courses
    @GetMapping("/")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();

        if (courses == null || courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // [GET] View a specific Course
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long courseId) {
        Course course = courseService.getCourseById(courseId);

        if (course == null) {
            return new ResponseEntity("Course with id " + courseId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Course>(course, HttpStatus.OK);
    }

    // [POST] Create a Course
    @PostMapping("/")
    public ResponseEntity createCourse(@Valid @RequestBody Course course) {
        courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // [PUT] Update a specific Course
    @PutMapping("/{courseId}")
    public ResponseEntity updateCourseById(@PathVariable("courseId") Long courseId, @RequestBody Course course) {
        courseService.updateCourseById(courseId, course.getName(), course.getDuration());
        return ResponseEntity.ok().build();
    }

    // [DELETE] Remove a specific Course
    @DeleteMapping("/{courseId}")
    public ResponseEntity deleteCourseById(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourseById(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // [GET] View All Courses By Lecturer Id
    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<List<Course>> viewAllCoursesByLecturerId(@PathVariable Long lecturerId) {

        List<Course> courses = courseService.viewAllCoursesByLecturerId(lecturerId);

        if (courses == null || courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(courses);
    }

    // [POST] Add a Course to a specific Lecturer
    @PostMapping("/{courseId}/lecturer/{lecturerId}")
    public ResponseEntity addCourseToLecturer(@PathVariable("lecturerId") Long lecturerId,
                                              @PathVariable("courseId") Long courseId) {
        courseService.addCourseToLecturer(lecturerId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // [DELETE] Remove a specific Course assigned to a Lecturer
    @DeleteMapping("/{courseId}/lecturer/{lecturerId}")
    public ResponseEntity removeCourseFromLecturer(@PathVariable("lecturerId") Long lecturerId,
                                                   @PathVariable("courseId") Long courseId) {
        courseService.removeCourseFromLecturer(lecturerId, courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
