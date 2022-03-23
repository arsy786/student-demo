package dev.arsalaan.studentdemo.controller;

import dev.arsalaan.studentdemo.exception.ApiRequestException;
import dev.arsalaan.studentdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.arsalaan.studentdemo.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
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
    public ResponseEntity createStudent(@RequestBody Student student) {
        int num = studentService.createStudent(student);

        if (num == 1) {
            return new ResponseEntity("email taken", HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}")
        public ResponseEntity updateStudent(@PathVariable("studentId") Long studentId, @RequestBody Student student) {
        int val = studentService.updateStudent(studentId, student.getName(), student.getEmail());

        if (val == 1) {
            return new ResponseEntity("student with id " + studentId + " does not exist", HttpStatus.NOT_FOUND);
        }

        if (val == 2) {
            return new ResponseEntity("email taken", HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity deleteStudentById(@PathVariable("studentId") Long studentId) {
        boolean exists = studentService.deleteStudentById(studentId);

        if (exists) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity("student with id " + studentId + " does not exist", HttpStatus.NOT_FOUND);
    }

}
