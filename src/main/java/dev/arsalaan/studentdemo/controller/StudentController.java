package dev.arsalaan.studentdemo.controller;

import dev.arsalaan.studentdemo.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.arsalaan.studentdemo.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();

        if (students == null || students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("studentId") Long studentId) {
        Student student = studentService.getStudentById(studentId);

        if (student == null) {
            return new ResponseEntity("student with id " + studentId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity createStudent(@Valid @RequestBody Student student) {
        studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{studentId}")
        public ResponseEntity updateStudent(@PathVariable("studentId") Long studentId, @RequestBody Student student) {
        studentService.updateStudent(studentId, student.getName(), student.getEmail());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity deleteStudentById(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudentById(studentId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // [GET] View All Students By Course Id
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Student>> viewAllStudentsByCourseId(@PathVariable Long courseId) {

        List<Student> students = studentService.viewAllStudentsByCourseId(courseId);

        if (students == null || students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(students);
    }

    // [POST] Add a Course to a specific Lecturer
    @PostMapping("/{studentId}/course/{courseId}")
    public ResponseEntity addStudentToCourse(@PathVariable("courseId") Long courseId,
                                              @PathVariable("studentId") Long studentId) {
        studentService.addStudentToCourse(courseId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // [DELETE] Remove a specific Course assigned to a Lecturer
    @DeleteMapping("/{studentId}/course/{courseId}")
    public ResponseEntity removeStudentFromCourse(@PathVariable("courseId") Long courseId,
                                                  @PathVariable("studentId") Long studentId) {
        studentService.removeStudentFromCourse(courseId, studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
